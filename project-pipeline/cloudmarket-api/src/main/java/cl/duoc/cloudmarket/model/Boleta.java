package cl.duoc.cloudmarket.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Boleta representa una boleta de compra generada en CloudMarket.
 * Contiene el detalle de productos comprados, subtotal, IVA y total.
 */
@Entity
@Table(name = "boletas")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreCliente;

    @Column(nullable = false)
    private String rutCliente;

    // Relacion uno a muchos: una boleta tiene varios detalles de productos
    // CascadeType.ALL: al guardar la boleta, tambien se guardan sus detalles
    // FetchType.EAGER: los detalles se cargan junto con la boleta automaticamente
    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetalleBoleta> detalle = new ArrayList<>();

    // Subtotal = suma de (precio * cantidad) por cada producto
    @Column(nullable = false)
    private Long subtotal;

    // IVA = 19% del subtotal
    @Column(nullable = false)
    private Long iva;

    // Total = subtotal + iva
    @Column(nullable = false)
    private Long total;

    // Estado de la boleta, por ejemplo: "GENERADA"
    @Column(nullable = false)
    private String estado;

    // -------------------------------------------------------
    // Constructores
    // -------------------------------------------------------

    public Boleta() {
    }

    // -------------------------------------------------------
    // Getters y Setters
    // -------------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<DetalleBoleta> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleBoleta> detalle) {
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
