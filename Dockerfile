# Use official OpenJDK 8
FROM openjdk:8-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar file into the container
COPY target/*.jar app.jar

# Expose the port your Spring Boot app uses
EXPOSE 8080

# Run the Spring Boot jar
ENTRYPOINT ["java", "-jar", "app.jar"]
