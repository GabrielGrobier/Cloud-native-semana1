package cl.duoc.notificaciones;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {
    private final RegistroNotificacionRepository repository;
    public NotificacionController(RegistroNotificacionRepository repository) {
        this.repository = repository;
    }

    public record RegistrarVentaRequest(@NotNull Long idBoleta, @NotNull @Positive Long total) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroNotificacion registrar(@Valid @RequestBody RegistrarVentaRequest request) {
        return repository.save(new RegistroNotificacion(request.idBoleta(), request.total()));
    }

    @GetMapping
    public List<RegistroNotificacion> listar() {
        return repository.findAll();
    }
}
