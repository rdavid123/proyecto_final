# Usa la imagen de OpenJDK 17 como base
FROM openjdk:17

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor en /app
COPY target/aquaclean.jar /app

# Expone el puerto 8080 para que Render.com pueda acceder a tu aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "aquaclean.jar"]
