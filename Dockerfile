# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Copy the built JAR file
COPY target/SmartAuthority-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 80

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"] 