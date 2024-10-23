# Liquibase Project

This project is a Spring Boot application that uses Liquibase for database migrations. It includes configurations for running the application with Docker and Docker Compose, as well as integrating with SonarQube for code quality analysis.

### Prerequisites

In order to run and use the application, you need to have installed and configured the following software:
* Java 21
* Maven
* Docker
* Docker Compose
* Git

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Cloning the Repository
```
git clone https://github.com/stefangolubov/liquibase-project.git
cd liquibase-project
```
## Building the application

To build the application, run:
```
mvnw clean package -DskipTests
```
## Running the Application with Docker Compose
To run the application along with PostgreSQL and SonarQube, use Docker Compose:

```
docker-compose up --build
```
This will start the following services:
* **db:** PostgreSQL database
* **app:** Spring Boot application
* **sonarqube:** SonarQube server

## Running the Application with Docker
If you prefer to run the application using Docker without Docker Compose, you need to have a running instances of PostgreSQL and SonarQube. In order to do that, follow these steps:

### Build the Docker image
```
docker build -t liquibase-project .
```
### Create a Docker Network
```
docker network create my_network
```
### Run PostgreSQL Container
```
docker run -d --name postgres --network my_network -e POSTGRES_DB=postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=my_password postgres:latest
```
### Run SonarQube container
```
docker run -d --name sonarqube --network my_network -p 9000:9000 -e SONARQUBE_JDBC_URL=jdbc:postgresql://postgres:5432/postgres -e SONARQUBE_JDBC_USERNAME=postgres -e SONARQUBE_JDBC_PASSWORD=my_password sonarqube:latest
```
### Run the Application Container
```
docker run -p 8888:8888 --network my_network -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/postgres -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=my_password liquibase-project
```

## Accessing the application
* The application will be available at http://localhost:8888.
* Swagger UI can be accessed at http://localhost:8888/swagger-ui-liquibase-project.html.
* SonarQube can be accessed at http://localhost:9000.

## Configuration
* The application configuration is defined in application.yml
* SonarQube configuration is defined in sonar-project.yml

    ```
    sonar:
      projectKey: liquibase_project
      host:
        url: http://localhost:9000
      login: your_sonarqube_login_token
      coverage:
        jacoco:
          xmlReportPaths: target/jacoco/jacoco.xml
    ```
    Once you have installed SonarQube, access SonarQube, create a new project and generate a login token. Replace your_sonarqube_login_token with the SonarQube login token you have generated.

### Running SonarQube Analysis
To run the SonarQube analysis, use the following Maven command:
```
./mvnw sonar:sonar
```
## Testing
To run the tests, use:
```
./mvnw test
```
## Additional Information
* Dockerfile: Defines the Docker image for the application.
* docker-compose.yml: Defines the Docker Compose configuration for running the application along with PostgreSQL and SonarQube. 
* application-it.yml: Configuration for integration tests using Testcontainers.

## Authors

* **Stefan Golubov** [@stefangolubov](https://github.com/stefangolubov)

