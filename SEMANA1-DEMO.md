# Desarrollo Cloud Native — demostracion de Semana 1

Este repositorio es un ejemplo docente, no una solucion completa de la evaluacion.
Demuestra el flujo: GitHub Actions -> pruebas Maven -> Docker Hub -> AWS EC2 (Docker Compose).

## Servicios
- `cloudmarket-api`: REST de productos y boletas, puerto 8080; publica un evento local tras registrar una venta.
- `notificaciones-api`: REST `POST /api/notificaciones` y `GET /api/notificaciones`, puerto interno 8081; persiste los eventos recibidos.
- La comunicacion entre servicios es HTTP y ocurre tras confirmar la transaccion de la boleta. Si falla, se registra una advertencia y la compra no se revierte. En produccion se recomendaria implementar reintentos/outbox.

## Pruebas automatizadas
- Al abrir un Pull Request hacia `main`, GitHub Actions ejecuta `mvn clean test` en ambos proyectos con el perfil `test`.
- Cuando se hace push a `main`, se vuelven a ejecutar las pruebas; si pasan, se publican dos imagenes AMD64/ARM64 en Docker Hub y se despliegan en EC2.
- Los Dockerfiles omiten las pruebas al empaquetar porque ya son obligatorias en el job previo `tests`.

Para ejecutar localmente con Java 21 y Maven:
```bash
(cd project-pipeline/cloudmarket-api && SPRING_PROFILES_ACTIVE=test mvn test)
(cd project-pipeline/notificaciones-api && SPRING_PROFILES_ACTIVE=test mvn test)
```

## Persistencia
- Por defecto, H2 almacena datos en archivos dentro de volumenes Docker para cada servicio.
- El perfil `oracle` es opcional. Usa Oracle JDBC y necesita una instancia real en Oracle Cloud: **este proyecto no la crea**.
- Para usar Oracle en GitHub Actions / EC2: configurar Actions variable `DB_PROFILE=oracle` y secrets:
  `ORACLE_JDBC_URL`, `ORACLE_USERNAME`, `ORACLE_PASSWORD`,
  `AUDIT_ORACLE_JDBC_URL`, `AUDIT_ORACLE_USERNAME`, `AUDIT_ORACLE_PASSWORD`.
- Se recomiendan credenciales/esquemas independientes por servicio. Si la base Oracle requiere wallet/mTLS, es necesario configurarlo adicionalmente.

## Variables de despliegue
Conservar los secrets existentes:
`DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN`, `DOCKER_IMAGE_NAME`, `EC2_HOST`, `EC2_USER`, `EC2_SSH_KEY`.

Para Docker Compose local: copiar `.env.example` a `.env`, ajustar el usuario/nombre de imagen y asegurarse de que ambas imagenes estan disponibles en Docker Hub:
```bash
cp .env.example .env
docker compose pull
docker compose up -d
curl http://localhost/api/productos
```

Para probar el segundo servicio dentro de la red de Compose:
```bash
docker compose exec notificaciones-api sh -c 'wget -qO- http://localhost:8081/api/notificaciones'
```

**Importante:** esta rama no despliega automaticamente a EC2 ni publica imagenes al abrir el PR.
Antes de fusionarla, verificar los tests y que las credenciales sean validas. El esquema de Oracle y el acceso a EC2 deben comprobarse en un entorno real.
