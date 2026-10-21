package cl.duoc.notificaciones;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones_ventas")
public class RegistroNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long idBoleta;
    @Column(nullable = false)
    private Long total;
    @Column(nullable = false)
    private LocalDateTime creadaEn;

    protected RegistroNotificacion() {}

    public RegistroNotificacion(Long idBoleta, Long total) {
        this.idBoleta = idBoleta;
        this.total = total;
        this.creadaEn = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getIdBoleta() { return idBoleta; }
    public Long getTotal() { return total; }
    public LocalDateTime getCreadaEn() { return creadaEn; }
}
