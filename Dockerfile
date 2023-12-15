# Usa la imagen de OpenJDK 17 como base
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17.0.1-jdk-slim
#COPY --from=build /target/aquaclean.jar

# Copia el archivo JAR de tu aplicación al contenedor en /app
COPY target/aquaclean.jar /app

# Expone el puerto 8080 para que Render.com pueda acceder a tu aplicación
EXPOSE 8080


# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "aquaclean.jar"]
