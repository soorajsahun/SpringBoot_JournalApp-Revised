# Use Maven + JDK to build
FROM maven:3.9.3-openjdk-8 AS build

WORKDIR /app

# Copy source code
COPY pom.xml .
COPY src ./src

# Build the jar
RUN mvn clean package -DskipTests

# Use lightweight JDK for running
FROM openjdk:8-jdk-alpine
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
