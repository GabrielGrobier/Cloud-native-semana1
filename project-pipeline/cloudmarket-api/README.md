# CloudMarket API

API REST para administrar productos y generar boletas de compra de una tienda tecnológica llamada **CloudMarket**.

---

## Tecnologías utilizadas

| Tecnología         | Versión   | Descripción                              |
|--------------------|-----------|------------------------------------------|
| Java               | 21        | Lenguaje de programación                 |
| Spring Boot        | 3.3.5     | Framework principal de la aplicación     |
| Spring Web         | -         | Creación de endpoints REST               |
| Spring Data JPA    | -         | Acceso a base de datos con repositorios  |
| H2 Database        | -         | Base de datos embebida en archivo        |
| Validation         | -         | Validación de datos de entrada           |
| Maven              | 3.x       | Gestor de dependencias y compilación     |
| Docker             | -         | Empaquetado y despliegue en contenedor   |

---

## Estructura del proyecto

```
cloudmarket-api/
├── src/
│   ├── main/
│   │   ├── java/cl/duoc/cloudmarket/
│   │   │   ├── CloudmarketApiApplication.java   # Clase principal
│   │   │   ├── DataInitializer.java             # Datos iniciales al arrancar
│   │   │   ├── controller/
│   │   │   │   ├── ProductoController.java
│   │   │   │   └── BoletaController.java
│   │   │   ├── service/
│   │   │   │   ├── ProductoService.java
│   │   │   │   └── BoletaService.java
│   │   │   ├── repository/
│   │   │   │   ├── ProductoRepository.java
│   │   │   │   └── BoletaRepository.java
│   │   │   ├── model/
│   │   │   │   ├── Producto.java
│   │   │   │   ├── Boleta.java
│   │   │   │   └── DetalleBoleta.java
│   │   │   ├── dto/
│   │   │   │   ├── CrearProductoRequest.java
│   │   │   │   ├── CrearBoletaRequest.java
│   │   │   │   ├── ProductoCantidadRequest.java
│   │   │   │   ├── BoletaResponse.java
│   │   │   │   └── DetalleBoletaResponse.java
│   │   │   └── exception/
│   │   │       ├── ResourceNotFoundException.java
│   │   │       ├── StockInsuficienteException.java
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties
├── Dockerfile
└── pom.xml
```

---

## Endpoints disponibles

### Productos

| Método | Endpoint          | Descripción                   |
|--------|-------------------|-------------------------------|
| GET    | /api/productos    | Lista todos los productos      |
| POST   | /api/productos    | Crea un nuevo producto         |

### Boletas

| Método | Endpoint           | Descripción                        |
|--------|--------------------|------------------------------------|
| POST   | /api/boletas       | Genera una nueva boleta de compra  |
| GET    | /api/boletas       | Lista todas las boletas generadas  |
| GET    | /api/boletas/{id}  | Consulta una boleta por su id      |

---

## Ejemplos JSON para probar

### 1. Listar productos

**Request:**
```
GET http://localhost:8080/api/productos
```

**Response (ejemplo):**
```json
[
  {
    "id": 1,
    "nombre": "Mouse Gamer RGB",
    "descripcion": "Mouse inalambrico con iluminacion RGB ajustable",
    "categoria": "Perifericos",
    "precio": 15990,
    "stock": 20,
    "estado": "ACTIVO"
  }
]
```

---

### 2. Crear un producto

**Request:**
```
POST http://localhost:8080/api/productos
Content-Type: application/json
```

**Body:**
```json
{
  "nombre": "Notebook Lenovo IdeaPad",
  "descripcion": "Notebook Ryzen 5, 16GB RAM, SSD 512GB",
  "categoria": "Computadores",
  "precio": 549990,
  "stock": 5
}
```

**Response (HTTP 201):**
```json
{
  "id": 7,
  "nombre": "Notebook Lenovo IdeaPad",
  "descripcion": "Notebook Ryzen 5, 16GB RAM, SSD 512GB",
  "categoria": "Computadores",
  "precio": 549990,
  "stock": 5,
  "estado": "ACTIVO"
}
```

---

### 3. Generar una boleta de compra

**Request:**
```
POST http://localhost:8080/api/boletas
Content-Type: application/json
```

**Body:**
```json
{
  "nombreCliente": "Camila Rojas",
  "rutCliente": "12.345.678-9",
  "productos": [
    {
      "productoId": 1,
      "cantidad": 2
    },
    {
      "productoId": 2,
      "cantidad": 1
    }
  ]
}
```

**Response (HTTP 201):**
```json
{
  "idBoleta": 1,
  "nombreCliente": "Camila Rojas",
  "rutCliente": "12.345.678-9",
  "detalle": [
    {
      "producto": "Mouse Gamer RGB",
      "cantidad": 2,
      "precioUnitario": 15990,
      "subtotalProducto": 31980
    },
    {
      "producto": "Teclado Mecanico",
      "cantidad": 1,
      "precioUnitario": 39990,
      "subtotalProducto": 39990
    }
  ],
  "subtotal": 71970,
  "iva": 13674,
  "total": 85644,
  "estado": "GENERADA"
}
```

---

### 4. Listar todas las boletas

**Request:**
```
GET http://localhost:8080/api/boletas
```

---

### 5. Consultar boleta por id

**Request:**
```
GET http://localhost:8080/api/boletas/1
```

---

## Cálculo de la boleta

| Concepto             | Fórmula                                  | Ejemplo           |
|----------------------|------------------------------------------|-------------------|
| Subtotal producto    | `precioUnitario × cantidad`              | 15990 × 2 = 31980 |
| Subtotal general     | Suma de todos los subtotales de productos| 31980 + 39990 = 71970 |
| IVA (19%)            | `(long)(subtotal × 0.19)`               | (long)(71970 × 0.19) = 13674 |
| Total final          | `subtotal + iva`                         | 71970 + 13674 = 85644 |

> El IVA se calcula sobre el subtotal general y se aplica truncamiento (sin redondeo).

---

## Comandos para ejecutar localmente

### Requisitos previos
- Java 21 instalado
- Maven 3.x instalado

### Compilar el proyecto
```bash
mvn clean package
```

### Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación quedará disponible en: `http://localhost:8080`

### Consola H2 (base de datos)
Acceder a `http://localhost:8080/h2-console` con:
- **JDBC URL:** `jdbc:h2:file:/app/data/cloudmarketdb`
- **User Name:** `sa`
- **Password:** (vacío)

---

## Comandos Docker

> El Dockerfile usa **multi-stage build**: la etapa `build` compila el proyecto con Maven
> y la etapa `runtime` solo incluye el JRE y el JAR final, produciendo una imagen más liviana.

### 1. Construir la imagen Docker (compila automáticamente)
```bash
docker build -t cloudmarket-api .
```

### 2. Ejecutar el contenedor
```bash
docker run -p 8080:8080 cloudmarket-api
```

### 4. Ejecutar con volumen para persistir la base de datos H2
```bash
docker run -p 8080:8080 -v cloudmarket-data:/app/data cloudmarket-api
```

### 5. Ejecutar en segundo plano
```bash
docker run -d -p 8080:8080 --name cloudmarket cloudmarket-api
```

### 6. Ver logs del contenedor
```bash
docker logs cloudmarket
```

### 7. Detener el contenedor
```bash
docker stop cloudmarket
```

---

## Evidencia esperada para probar el funcionamiento

Para demostrar que el microservicio funciona correctamente, se recomienda evidenciar:

1. **Compilación exitosa:**  
   Ejecutar `mvn clean package` y capturar la salida con `BUILD SUCCESS`.

2. **Inicio de la aplicación:**  
   Ejecutar `mvn spring-boot:run` y capturar el mensaje de inicio de Spring Boot con el puerto 8080.

3. **Listar productos iniciales:**  
   `GET /api/productos` → Verificar que se retorna la lista de 6 productos precargados.

4. **Crear un nuevo producto:**  
   `POST /api/productos` → Verificar que retorna HTTP 201 con el producto creado.

5. **Generar una boleta:**  
   `POST /api/boletas` → Verificar que retorna HTTP 201 con la boleta calculada (subtotal, IVA, total).

6. **Validar descuento de stock:**  
   Comparar el stock del producto antes y después de generar la boleta usando `GET /api/productos`.

7. **Consultar boleta generada:**  
   `GET /api/boletas/1` → Verificar que se puede recuperar la boleta guardada.

8. **Error por stock insuficiente:**  
   Intentar comprar más unidades de las disponibles → Verificar que retorna HTTP 400 con mensaje de error claro.

9. **Error por producto inexistente:**  
   Usar un `productoId` que no exista → Verificar que retorna HTTP 404.

10. **Imagen Docker construida y ejecutada:**  
    `docker build` y `docker run` → Verificar que la API responde en `http://localhost:8080/api/productos`.
