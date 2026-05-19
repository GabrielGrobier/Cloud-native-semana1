package cl.duoc.cloudmarket.controller;

import cl.duoc.cloudmarket.dto.CrearProductoRequest;
import cl.duoc.cloudmarket.model.Producto;
import cl.duoc.cloudmarket.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ProductoController expone los endpoints REST para administrar productos.
 *
 * GET  /api/productos  -> lista todos los productos
 * POST /api/productos  -> crea un nuevo producto
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Lista todos los productos disponibles.
     *
     * @return lista de productos con HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    /**
     * Crea un nuevo producto en CloudMarket.
     * Valida los campos del request con @Valid.
     *
     * @param request datos del producto a crear
     * @return producto creado con HTTP 201
     */
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody CrearProductoRequest request) {
        Producto productoCreado = productoService.crearProducto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }
}
