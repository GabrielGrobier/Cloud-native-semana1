package cl.duoc.cloudmarket.dto;

/**
 * DTO que representa el detalle de un producto dentro de la respuesta de una boleta.
 */
public class DetalleBoletaResponse {

    private String producto;
    private Integer cantidad;
    private Long precioUnitario;
    private Long subtotalProducto;

    // -------------------------------------------------------
    // Constructores
    // -------------------------------------------------------

    public DetalleBoletaResponse() {
    }

    public DetalleBoletaResponse(String producto, Integer cantidad,
                                  Long precioUnitario, Long subtotalProducto) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotalProducto = subtotalProducto;
    }

    // -------------------------------------------------------
    // Getters y Setters
    // -------------------------------------------------------

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Long precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Long getSubtotalProducto() {
        return subtotalProducto;
    }

    public void setSubtotalProducto(Long subtotalProducto) {
        this.subtotalProducto = subtotalProducto;
    }
}
