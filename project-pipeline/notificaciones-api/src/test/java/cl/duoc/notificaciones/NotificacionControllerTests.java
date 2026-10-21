package cl.duoc.notificaciones;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NotificacionControllerTests {
    @Autowired MockMvc mvc;

    @Test
    void guardaYListaNotificaciones() throws Exception {
        mvc.perform(post("/api/notificaciones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"idBoleta\":99,\"total\":11900}"))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.idBoleta").value(99));

        mvc.perform(get("/api/notificaciones"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].idBoleta").value(99));
    }
}
