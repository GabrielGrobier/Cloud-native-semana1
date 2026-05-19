package cl.duoc.cloudmarket.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * DTO para recibir los datos al generar una nueva boleta de compra.
 */
public class CrearBoletaRequest {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @NotBlank(message = "El RUT del cliente es obligatorio")
    private String rutCliente;

    @NotEmpty(message = "Debe incluir al menos un producto en la boleta")
    @Valid
    private List<ProductoCantidadRequest> productos;

    // -------------------------------------------------------
    // Constructores
    // -------------------------------------------------------

    public CrearBoletaRequest() {
    }

    // -------------------------------------------------------
    // Getters y Setters
    // -------------------------------------------------------

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getRutCliente() {
        return rutCliente;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente = rutCliente;
    }

    public List<ProductoCantidadRequest> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCantidadRequest> productos) {
        this.productos = productos;
    }
}
