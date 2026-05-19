package cl.duoc.cloudmarket.service;

import cl.duoc.cloudmarket.dto.BoletaResponse;
import cl.duoc.cloudmarket.dto.CrearBoletaRequest;
import cl.duoc.cloudmarket.dto.DetalleBoletaResponse;
import cl.duoc.cloudmarket.dto.ProductoCantidadRequest;
import cl.duoc.cloudmarket.exception.ResourceNotFoundException;
import cl.duoc.cloudmarket.exception.StockInsuficienteException;
import cl.duoc.cloudmarket.model.Boleta;
import cl.duoc.cloudmarket.model.DetalleBoleta;
import cl.duoc.cloudmarket.model.Producto;
import cl.duoc.cloudmarket.repository.BoletaRepository;
import cl.duoc.cloudmarket.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * BoletaService contiene la logica de negocio para generar y consultar boletas.
 *
 * Calculo de la boleta:
 *   - subtotalProducto = precioUnitario * cantidad
 *   - subtotal         = suma de todos los subtotalProducto
 *   - iva              = (long)(subtotal * 0.19)   [IVA 19%]
 *   - total            = subtotal + iva
 */
@Service
public class BoletaService {

    private final BoletaRepository boletaRepository;
    private final ProductoRepository productoRepository;

    // Inyeccion de dependencias por constructor
    public BoletaService(BoletaRepository boletaRepository,
                         ProductoRepository productoRepository) {
        this.boletaRepository = boletaRepository;
        this.productoRepository = productoRepository;
    }

    // -------------------------------------------------------
    // Generar boleta de compra
    // -------------------------------------------------------

    /**
     * Genera una boleta de compra a partir de la solicitud recibida.
     * Valida existencia de productos, stock disponible,
     * calcula totales y descuenta el stock de cada producto.
     *
     * @Transactional garantiza que si algo falla en el medio,
     * todos los cambios se revierten automaticamente.
     */
    @Transactional
    public BoletaResponse generarBoleta(CrearBoletaRequest request) {

        // 1. Crear la boleta con los datos del cliente
        Boleta boleta = new Boleta();
        boleta.setNombreCliente(request.getNombreCliente());
        boleta.setRutCliente(request.getRutCliente());
        boleta.setEstado("GENERADA");

        // 2. Procesar cada producto solicitado
        List<DetalleBoleta> detalles = new ArrayList<>();
        long subtotal = 0;

        for (ProductoCantidadRequest item : request.getProductos()) {

            // 2a. Buscar el producto en la base de datos
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Producto no encontrado con id: " + item.getProductoId()));

            // 2b. Validar que haya stock suficiente
            if (producto.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para el producto '" + producto.getNombre() + "'. " +
                        "Stock disponible: " + producto.getStock() + ", cantidad solicitada: " + item.getCantidad());
            }

            // 2c. Calcular subtotal de esta linea
            long subtotalProducto = producto.getPrecio() * item.getCantidad();

            // 2d. Crear el detalle de esta linea
            DetalleBoleta detalle = new DetalleBoleta();
            detalle.setBoleta(boleta);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotalProducto(subtotalProducto);

            detalles.add(detalle);
            subtotal += subtotalProducto;

            // 2e. Descontar el stock del producto
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        // 3. Calcular IVA (19%) y total final
        long iva   = (long) (subtotal * 0.19);
        long total = subtotal + iva;

        // 4. Completar y guardar la boleta
        boleta.setDetalle(detalles);
        boleta.setSubtotal(subtotal);
        boleta.setIva(iva);
        boleta.setTotal(total);

        Boleta boletaGuardada = boletaRepository.save(boleta);

        // 5. Retornar la respuesta mapeada
        return mapearAResponse(boletaGuardada);
    }

    // -------------------------------------------------------
    // Listar todas las boletas
    // -------------------------------------------------------

    public List<BoletaResponse> listarBoletas() {
        List<Boleta> boletas = boletaRepository.findAll();
        List<BoletaResponse> respuestas = new ArrayList<>();
        for (Boleta boleta : boletas) {
            respuestas.add(mapearAResponse(boleta));
        }
        return respuestas;
    }

    // -------------------------------------------------------
    // Obtener una boleta por id
    // -------------------------------------------------------

    public BoletaResponse obtenerBoleta(Long id) {
        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Boleta no encontrada con id: " + id));
        return mapearAResponse(boleta);
    }

    // -------------------------------------------------------
    // Metodo privado: mapear entidad Boleta a BoletaResponse
    // -------------------------------------------------------

    private BoletaResponse mapearAResponse(Boleta boleta) {

        // Mapear cada DetalleBoleta a DetalleBoletaResponse
        List<DetalleBoletaResponse> detalleResponse = new ArrayList<>();
        for (DetalleBoleta d : boleta.getDetalle()) {
            DetalleBoletaResponse item = new DetalleBoletaResponse(
                    d.getProducto().getNombre(),
                    d.getCantidad(),
                    d.getPrecioUnitario(),
                    d.getSubtotalProducto()
            );
            detalleResponse.add(item);
        }

        return new BoletaResponse(
                boleta.getId(),
                boleta.getNombreCliente(),
                boleta.getRutCliente(),
                detalleResponse,
                boleta.getSubtotal(),
                boleta.getIva(),
                boleta.getTotal(),
                boleta.getEstado()
        );
    }
}
