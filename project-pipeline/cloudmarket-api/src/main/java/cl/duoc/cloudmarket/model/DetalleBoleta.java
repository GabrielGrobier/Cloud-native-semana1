package cl.duoc.cloudmarket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad DetalleBoleta representa cada linea de productos dentro de una boleta.
 * Por ejemplo: 2 unidades de "Mouse Gamer RGB" a $15.990 c/u = subtotal $31.980.
 */
@Entity
@Table(name = "detalle_boletas")
public class DetalleBoleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Referencia a la boleta a la que pertenece este detalle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boleta_id", nullable = false)
    private Boleta boleta;

    // Referencia al producto comprado
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    // Precio unitario al momento de la compra
    @Column(nullable = false)
    private Long precioUnitario;

    // Subtotal de esta linea = precioUnitario * cantidad
    @Column(nullable = false)
    private Long subtotalProducto;

    // -------------------------------------------------------
    // Constructores
    // -------------------------------------------------------

    public DetalleBoleta() {
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

    public Boleta getBoleta() {
        return boleta;
    }

    public void setBoleta(Boleta boleta) {
        this.boleta = boleta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
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
