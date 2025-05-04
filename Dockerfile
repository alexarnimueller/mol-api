# Use an ARM-compatible base image for Java 17
FROM --platform=linux/arm64 eclipse-temurin:17-jdk

# Set the working directory
WORKDIR /app

# Copy the jar file into the image
COPY target/molecule-api-1.0.0.jar app.jar

# Expose port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
