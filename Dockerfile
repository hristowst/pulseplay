# Stage 1: Build the app using Maven
FROM maven:3.9.7-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src src/
# If using mvnw, copy it too (but Maven image has mvn, so optional)
COPY mvnw mvnw.cmd ./
RUN chmod +x mvnw  # Only if using mvnw; skip if using mvn
RUN ./mvnw clean package -DskipTests  # Or: mvn clean package -DskipTests
# The JAR will be in target/ (e.g., your-app-0.0.1-SNAPSHOT.jar)

# Stage 2: Run the app
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]