# Etapa de construcción
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /usr/src/app

# Copia solo el archivo de descripción de dependencias (pom.xml)
COPY pom.xml .

# Descarga las dependencias
RUN mvn dependency:go-offline

# Copia el resto del código fuente y construye la aplicación
COPY src src
RUN mvn package -DskipTests

# Etapa de ejecución
FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

# Copia el JAR construido desde la etapa de construcción
COPY --from=build /usr/src/app/target/aquaclean.jar /app/aquaclean.jar

# Expone el puerto 8080 para que Render.com pueda acceder a tu aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "aquaclean.jar"]
