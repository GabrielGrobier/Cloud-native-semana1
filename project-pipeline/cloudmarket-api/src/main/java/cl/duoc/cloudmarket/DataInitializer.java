package cl.duoc.cloudmarket;

import cl.duoc.cloudmarket.model.Producto;
import cl.duoc.cloudmarket.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DataInitializer carga productos de ejemplo al iniciar la aplicacion,
 * solo si la base de datos esta vacia. Esto facilita las pruebas iniciales.
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ProductoRepository productoRepository) {
        return args -> {
            if (productoRepository.count() == 0) {
                System.out.println(">>> Cargando productos iniciales en CloudMarket...");

                productoRepository.save(new Producto(
                        "Mouse Gamer RGB",
                        "Mouse inalambrico con iluminacion RGB ajustable, 6 botones programables",
                        "Perifericos",
                        15990L,
                        20,
                        "ACTIVO"
                ));

                productoRepository.save(new Producto(
                        "Teclado Mecanico",
                        "Teclado mecanico TKL, switches Red, retroiluminado RGB",
                        "Perifericos",
                        39990L,
                        15,
                        "ACTIVO"
                ));

                productoRepository.save(new Producto(
                        "Monitor 24 FHD",
                        "Monitor Full HD 24 pulgadas, 75Hz, panel IPS, sin bordes",
                        "Monitores",
                        149990L,
                        8,
                        "ACTIVO"
                ));

                productoRepository.save(new Producto(
                        "Notebook Lenovo IdeaPad",
                        "Notebook Ryzen 5, 16GB RAM, SSD 512GB",
                        "Computadores",
                        549990L,
                        5,
                        "ACTIVO"
                ));

                productoRepository.save(new Producto(
                        "Audifonos Bluetooth",
                        "Audifonos over-ear inalambricos con cancelacion de ruido activa",
                        "Audio",
                        79990L,
                        12,
                        "ACTIVO"
                ));

                productoRepository.save(new Producto(
                        "Disco SSD 1TB",
                        "Disco de estado solido NVMe PCIe 4.0, lectura 7000 MB/s",
                        "Almacenamiento",
                        89990L,
                        10,
                        "ACTIVO"
                ));

                System.out.println(">>> Productos iniciales creados correctamente. Total: " + productoRepository.count());
            } else {
                System.out.println(">>> Ya existen productos en la base de datos. No se cargaran datos iniciales.");
            }
        };
    }
}
