# Usa una imagen base de Java 17, que es la que tu proyecto necesita.
FROM eclipse-temurin:17-jdk-jammy

# Establece el directorio de trabajo dentro del contenedor.
WORKDIR /app

# Copia el archivo JAR compilado (que Maven crea en la carpeta 'target') al contenedor.
# El nombre 'app.jar' es un nombre genérico que le damos dentro del contenedor.
COPY target/*.jar app.jar

# El comando que se ejecutará cuando el contenedor se inicie.
ENTRYPOINT ["java", "-jar", "app.jar"]
