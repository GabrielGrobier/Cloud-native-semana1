# Desarrollo Cloud Native — demostracion de Semana 1

Este repositorio es un **ejemplo docente** para explicar el flujo de trabajo:
GitHub Actions -> pruebas Maven -> Docker Hub -> AWS EC2 (Docker Compose).
No reemplaza todas las instrucciones de una actividad evaluada.

## Microservicios

- `cloudmarket-api`: API REST de productos y boletas, puerto 8080.
- `notificaciones-api`: API REST con `POST /api/notificaciones` y
  `GET /api/notificaciones`, puerto interno 8081.
- Tras confirmar una venta, `cloudmarket-api` llama a `notificaciones-api` por HTTP
  para registrar una notificacion de demostracion.
  Si el servicio de notificaciones falla, la compra permanece confirmada. En produccion
  se recomendaria implementar reintentos, idempotencia o un patron outbox.

## Base de datos: solo H2

**No se requiere cuenta de Oracle Cloud.** Cada microservicio dispone de su propia
base H2 persistente en archivo y un volumen Docker independiente:

- `cloudmarket_data:/app/data`
- `notificaciones_data:/app/data`

En las pruebas automatizadas se utiliza **H2 en memoria** mediante el perfil `test`,
para evitar depender de servicios externos. La consola H2 esta deshabilitada por defecto.

**Importante para los estudiantes:** aunque este ejemplo usa H2, si la pauta de su
actividad solicita Oracle Cloud deben cumplir ese requisito en su entrega.

## Pruebas y automatizacion

En cada Pull Request hacia `main`, GitHub Actions ejecuta pruebas Maven de los dos
microservicios. En un push a `main`, si pasan las pruebas, se construyen y publican
las dos imagenes Docker para AMD64/ARM64 y se despliegan en EC2.

Para ejecutar las pruebas localmente con Java 21 y Maven:

```bash
(cd project-pipeline/cloudmarket-api && SPRING_PROFILES_ACTIVE=test mvn test)
(cd project-pipeline/notificaciones-api && SPRING_PROFILES_ACTIVE=test mvn test)
```

Los Dockerfiles no repiten las pruebas durante el empaquetado:
la validacion se realiza previamente mediante el job `tests`.

## Docker Compose local

Copia el archivo de variables y configura tu usuario/nombre de imagen (ambas imagenes
deben estar disponibles en Docker Hub):

```bash
cp .env.example .env
docker compose pull
docker compose up -d
curl http://localhost/api/productos
```

Puedes consultar las notificaciones desde el contenedor privado:

```bash
docker compose exec notificaciones-api sh -c 'wget -qO- http://localhost:8081/api/notificaciones'
```

## Secrets para GitHub Actions

Solo se conservan los necesarios para Docker Hub y EC2:
`DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN`, `DOCKER_IMAGE_NAME`,
`EC2_HOST`, `EC2_USER`, `EC2_SSH_KEY`.

El Pull Request ejecuta pruebas, **sin publicar imagenes ni desplegar EC2**.
El despliegue solo se realizara cuando los cambios se integren a `main`.
