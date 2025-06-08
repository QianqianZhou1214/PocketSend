# Stage 1: Build the application
FROM maven:3.9.5-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy the entire project to the working directory
COPY . .

# Use Maven to build the project and skip tests to speed up
RUN mvn clean package -DskipTests

# Stage 2: Create the production image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=builder /app/target/*.jar app.jar


# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
