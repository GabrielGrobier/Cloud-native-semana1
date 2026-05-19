package cl.duoc.cloudmarket.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO que representa un producto y la cantidad deseada dentro de una solicitud de boleta.
 */
public class ProductoCantidadRequest {

    @NotNull(message = "El id del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    // -------------------------------------------------------
    // Constructores
    // -------------------------------------------------------

    public ProductoCantidadRequest() {
    }

    // -------------------------------------------------------
    // Getters y Setters
    // -------------------------------------------------------

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
