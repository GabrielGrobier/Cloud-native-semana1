package cl.duoc.cloudmarket.controller;

import cl.duoc.cloudmarket.dto.BoletaResponse;
import cl.duoc.cloudmarket.dto.CrearBoletaRequest;
import cl.duoc.cloudmarket.service.BoletaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * BoletaController expone los endpoints REST para generar y consultar boletas.
 *
 * POST /api/boletas       -> genera una nueva boleta de compra
 * GET  /api/boletas       -> lista todas las boletas generadas
 * GET  /api/boletas/{id}  -> consulta una boleta especifica por id
 */
@RestController
@RequestMapping("/api/boletas")
public class BoletaController {

    private final BoletaService boletaService;

    public BoletaController(BoletaService boletaService) {
        this.boletaService = boletaService;
    }

    /**
     * Genera una nueva boleta de compra.
     * Valida los campos del request con @Valid.
     *
     * @param request datos de la boleta: cliente y lista de productos con cantidades
     * @return boleta generada con HTTP 201
     */
    @PostMapping
    public ResponseEntity<BoletaResponse> generarBoleta(@Valid @RequestBody CrearBoletaRequest request) {
        BoletaResponse boleta = boletaService.generarBoleta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(boleta);
    }

    /**
     * Lista todas las boletas generadas en CloudMarket.
     *
     * @return lista de boletas con HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<BoletaResponse>> listarBoletas() {
        List<BoletaResponse> boletas = boletaService.listarBoletas();
        return ResponseEntity.ok(boletas);
    }

    /**
     * Consulta una boleta especifica por su id.
     *
     * @param id identificador de la boleta
     * @return boleta encontrada con HTTP 200, o error 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoletaResponse> obtenerBoleta(@PathVariable Long id) {
        BoletaResponse boleta = boletaService.obtenerBoleta(id);
        return ResponseEntity.ok(boleta);
    }
}
