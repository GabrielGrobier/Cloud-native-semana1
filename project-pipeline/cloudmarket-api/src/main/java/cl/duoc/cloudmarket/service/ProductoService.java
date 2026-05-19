package cl.duoc.cloudmarket.service;

import cl.duoc.cloudmarket.dto.CrearProductoRequest;
import cl.duoc.cloudmarket.model.Producto;
import cl.duoc.cloudmarket.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductoService contiene la logica de negocio relacionada con los productos.
 * Se comunica con el repositorio para acceder a la base de datos.
 */
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    // Inyeccion de dependencias por constructor (buena practica)
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Retorna la lista de todos los productos registrados.
     */
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    /**
     * Crea y guarda un nuevo producto con estado "ACTIVO".
     *
     * @param request DTO con los datos del producto a crear
     * @return el producto guardado en la base de datos
     */
    public Producto crearProducto(CrearProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setCategoria(request.getCategoria());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setEstado("ACTIVO");

        return productoRepository.save(producto);
    }
}
