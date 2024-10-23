# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project files to the container
COPY . .

# Ensure mvnw is executable and has correct line endings
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix mvnw
RUN chmod +x ./mvnw

# Package the application
RUN ./mvnw package -DskipTests

# Expose the port the app runs on
EXPOSE 8888

# Run the application
ENTRYPOINT ["java", "-jar", "target/liquibase-project-2.3.1-SNAPSHOT.jar"]