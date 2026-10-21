package cl.duoc.cloudmarket.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestClient;

/** Demostración didáctica de comunicación entre dos microservicios por HTTP. */
@Component
public class NotificacionVentaListener {
    private static final Logger LOG = LoggerFactory.getLogger(NotificacionVentaListener.class);
    private final RestClient client;

    public NotificacionVentaListener(RestClient.Builder builder,
                                    @Value("${notificaciones.url}") String url) {
        this.client = builder.baseUrl(url).build();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void alRegistrarVenta(VentaRegistradaEvent event) {
        try {
            client.post()
                  .uri("/api/notificaciones")
                  .body(event)
                  .retrieve()
                  .toBodilessEntity();
        } catch (Exception ex) {
            // Best effort: no revertir compras confirmadas por fallo en notificaciones.
            LOG.warn("No fue posible notificar la compra {}: {}", event.idBoleta(), ex.getMessage());
        }
    }
}
