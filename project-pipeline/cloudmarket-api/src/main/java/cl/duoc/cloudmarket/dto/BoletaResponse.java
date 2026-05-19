package cl.duoc.cloudmarket.dto;

import java.util.List;

/**
 * DTO de respuesta que representa una boleta de compra generada en CloudMarket.
 * Contiene el resumen de productos comprados, subtotal, IVA y total.
 */
public class BoletaResponse {

    private Long idBoleta;
    private String nombreCliente;
    private String rutCliente;
    private List<DetalleBoletaResponse> detalle;

    // Suma de (precioUnitario * cantidad) de cada producto
    private Long subtotal;

    // IVA = 19% del subtotal
    private Long iva;

    // Total = subtotal + iva
    private Long total;

    // Estado de la boleta: "GENERADA"
    private String estado;

    // -------------------------------------------------------
    // Constructores
    // -------------------------------------------------------

    public BoletaResponse() {
    }

    public BoletaResponse(Long idBoleta, String nombreCliente, String rutCliente,
                           List<DetalleBoletaResponse> detalle,
                           Long subtotal, Long iva, Long total, String estado) {
        this.idBoleta = idBoleta;
        this.nombreCliente = nombreCliente;
        this.rutCliente = rutCliente;
        this.detalle = detalle;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.estado = estado;
    }

    // -------------------------------------------------------
    // Getters y Setters
    // -------------------------------------------------------

    public Long getIdBoleta() {
        return idBoleta;
    }

    public void setIdBoleta(Long idBoleta) {
        this.idBoleta = idBoleta;
    }

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

    public List<DetalleBoletaResponse> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleBoletaResponse> detalle) {
        this.detalle = detalle;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }

    public Long getIva() {
        return iva;
    }

    public void setIva(Long iva) {
        this.iva = iva;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
