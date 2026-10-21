package cl.duoc.cloudmarket;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CloudmarketEndpointsTests {
    @Autowired MockMvc mvc;

    @Test
    void listarProductosRetornaOk() throws Exception {
        mvc.perform(get("/api/productos")).andExpect(status().isOk());
    }

    @Test
    void agregarProductoRetornaCreado() throws Exception {
        String json = """
                {"nombre":"Producto demo", "descripcion":"Prueba CI", "categoria":"Demo",
                 "precio":1000, "stock":10}
                """;
        mvc.perform(post("/api/productos").contentType(MediaType.APPLICATION_JSON).content(json))
           .andExpect(status().isCreated());
    }
}
