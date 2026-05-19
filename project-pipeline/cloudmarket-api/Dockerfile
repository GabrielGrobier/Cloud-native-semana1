# -------------------------------------------------------
# ETAPA 1: BUILD
# Compila el proyecto y genera el JAR usando Maven
# -------------------------------------------------------
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copiar descriptor de dependencias primero (aprovecha cache de Docker)
COPY pom.xml .

# Descargar dependencias sin compilar el codigo fuente
RUN apk add --no-cache maven && \
    mvn dependency:go-offline -q

# Copiar el codigo fuente
COPY src ./src

# Compilar y empaquetar (sin ejecutar tests)
RUN mvn clean package -DskipTests -q

# -------------------------------------------------------
# ETAPA 2: RUNTIME
# Imagen final ligera solo con el JRE y el JAR generado
# -------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crear directorio para la base de datos H2 persistente
RUN mkdir -p /app/data

# Copiar unicamente el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicacion
ENTRYPOINT ["java", "-jar", "app.jar"]
