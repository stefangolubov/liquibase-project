# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

# [Unreleased]

### [1.0.3-SNAPSHOT] - 2024-08-27 [@stefangolubov](https://github.com/stefangolubov)
#### Added
- LiquibaseProjectController - Rest Controller implementing all three ApiDelegate interfaces
#### Changed
- api.yml - Remove href for e-mail
- application.properties - Set spring.liquibase.change-log property properly
- liquibase.properties - Get the changeLogFile value from application.properties
- pom.xml - Changing api and model package names to target folder. Changing configOptions in openapi-generator-maven-plugin. Deleting maven-antrun-plugin. Update version from 1.0.2-SNAPSHOT to 1.0.3-SNAPSHOT
#### Renamed
- GlobalExceptionHandler to ExceptionHandlerAdvice
#### Deleted
- ModelApiResponse - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- NewOrder - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- NewProduct - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- NewUser - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- Orders - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- OrdersApi - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- OrdersApiController - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- OrdersApiDelegate - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- OrdersApiDelegateImpl - The logic is moved to LiquibaseProjectController
- Products - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- ProductsApi - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- ProductsApiController - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- ProductsApiDelegate - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- ProductsApiDelegateImpl - The logic is moved to LiquibaseProjectController
- Users - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- UsersApi - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- UsersApiController - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- UsersApiDelegate - Due to changing configuration for generating classes by openapi-generator-maven-plugin.
- UsersApiDelegateImpl - The logic is moved to LiquibaseProjectController

### [1.0.2-SNAPSHOT] - 2024-08-26 [@stefangolubov](https://github.com/stefangolubov)
#### Changed
- api.yml - Move all schemas and paths from schema and path yml files to api.yml
#### Renamed
- UsersPathApiDelegateImpl to UsersApiDelegateImpl
- ProductsPathApiDelegateImpl to ProductsApiDelegateImpl
- OrdersPathApiDelegateImpl to OrdersApiDelegateImpl
- api.yml#components#schemas#ApiResponseSchema to api.yml#components#schemas#ApiResponse

### [1.0.1-SNAPSHOT] - 2024-08-26 [@stefangolubov](https://github.com/stefangolubov)
#### Changed
- application.properties - Adding properties for base API paths
- api.yml - Using properties for base API paths configured in application.properties
- pom.xml - Update version from 1.0.0-SNAPSHOT to 1.0.1-SNAPSHOT
- ApiResponseSchema - [automatically updated by OpenAPI generator]
- ApiUtil - [automatically updated by OpenAPI generator]
- NewOrder - [automatically updated by OpenAPI generator]
- NewProduct - [automatically updated by OpenAPI generator]
- NewUser - [automatically updated by OpenAPI generator]
- Orders - [automatically updated by OpenAPI generator]
- Products - [automatically updated by OpenAPI generator]
- Users - [automatically updated by OpenAPI generator]
#### Added
- OrdersPathApiController - [automatically created by OpenAPI generator]
- ProductsPathApiController - [automatically created by OpenAPI generator]
- UsersPathApiController - [automatically created by OpenAPI generator]
#### Renamed
- OrdersPathApi - Renamed OrdersApi
- OrdersPathApiDelegate - Renamed OrdersApiDelegate
- OrdersPathApiDelegateImpl - Renamed OrdersApiDelegateImpl
- ProductsPathApi - Renamed ProductsApi
- ProductsPathApiDelegate - Renamed ProductsApiDelegate
- ProductsPathApiDelegateImpl - Renamed ProductsApiDelegateImpl
- UsersPathApi - Renamed UsersApi
- UsersPathApiDelegate - Renamed UsersApiDelegate
- UsersPathApiDelegateImpl - Renamed UsersApiDelegateImpl
#### Deleted
- OrdersApiController - replaced by OrdersPathApiController
- ProductsApiController - replaced by ProductsPathApiController
- UsersApiController - replaced by UsersPathApiController

### [1.0.0-SNAPSHOT] - 2024-08-23 [@stefangolubov](https://github.com/stefangolubov)
#### Changed
- pom.xml - Update version from 0.0.13-SNAPSHOT to 1.0.0-SNAPSHOT

### [0.0.13-SNAPSHOT] - 2024-08-23 [@stefangolubov](https://github.com/stefangolubov)
#### Changed
- api.yml - Changing security schema configuration
- GlobalExceptionHandler - Adding more exception handling and setting example responses for each error handler
- orders.yml - Changing security and responses configuration
- OrdersApi - [automatically updated by OpenAPI generator]
- OrdersApiDelegate - [automatically updated by OpenAPI generator]
- OrdersApiDelegateImpl - Adding null safe check for adding and updating order
- products.yml- Changing security and responses configuration
- ProductsApi - [automatically updated by OpenAPI generator]
- ProductsApiDelegate - [automatically updated by OpenAPI generator]
- ProductsApiDelegateImpl - Adding null safe check for adding and updating product
- users.yml- Changing security and responses configuration
- UsersApi - [automatically updated by OpenAPI generator]
- UsersApiDelegate - [automatically updated by OpenAPI generator]
- UsersApiDelegateImpl - Adding null safe check for adding and updating user
- pom.xml - Update version from 0.0.12-SNAPSHOT to 0.0.13-SNAPSHOT
#### Added
- CustomAccessDeniedHandler - Access denied handler for handling forbidden access to a resource
- ExceptionConstants - Utility class for defining constants needed for GlobalExceptionHandler
- InternalServerErrorException - Exception for handling internal server error
- InvalidInputException - Exception for handling invalid input
- SecurityConfig - Configuration for roles and their respective access per methods
- SwaggerConfig - Configuration for Swagger
- UserDetailsServiceConfig - Configuring users with credentials for basic authentication
#### Deleted
- ErrorResponse - Deleting obsolete class

### [0.0.12-SNAPSHOT] - 2024-08-19 [@stefangolubov](https://github.com/stefangolubov)
#### Changed
- orders.yml - Adding 200 response object for post and delete
- OrdersApi - [automatically updated by OpenAPI generator]
- OrdersApiDelegate - [automatically updated by OpenAPI generator]
- OrdersApiDelegateImpl - Implementing error handling
- OrdersService - Improvement for implementing error handling
- products.yml - Adding 200 response object for post and delete
- ProductsApi - [automatically updated by OpenAPI generator]
- ProductsApiDelegate - [automatically updated by OpenAPI generator]
- ProductsApiDelegateImpl - Implementing error handling
- ProductsService - Improvement for implementing error handling
- orders.yml - Adding 200 response object for post and delete
- OrdersApi - [automatically updated by OpenAPI generator]
- OrdersApiDelegate - [automatically updated by OpenAPI generator]
- OrdersApiDelegateImpl - Implementing error handling
- OrdersService - Improvement for implementing error handling
- pom.xml - Update version from 0.0.11-SNAPSHOT to 0.0.12-SNAPSHOT
#### Added
- EmptyInputException - RunTimeException for handling empty user input
- ErrorResponse - Class for handling error response
- GlobalExceptionHandler - Controller advice for handling various types of exceptions
- ResultsNotFoundException - RunTimeException for handling empty response error for given input

### [0.0.11-SNAPSHOT] - 2024-08-16 [@stefangolubov](https://github.com/stefangolubov)
#### Renamed
- apiresponse.yml - Renamed to apiresponseschema.yml (due to ApiResponse being reserved word)
#### Changed
- api.yml - Changes after renaming ApiResponse schema object to ApiResponseSchema
- NewOrderMapper - Ignoring some entity attributes in the mapping
- NewProductsMapper - Ignoring some entity attributes in the mapping
- NewUserMapper - Ignoring some entity attributes in the mapping
- orders.yml - Changes after renaming ApiResponse schema
- OrdersApi - Changes after renaming ApiResponse schema
- OrdersApiController - Changes after renaming ApiResponse schema
- OrdersApiDelegate - Changes after renaming ApiResponse schema
- OrdersApiDelegateImpl - Changes after renaming ApiResponse schema
- OrdersMapper - Ignoring some entity attributes in the mapping
- products.yml - Changing ApiResponse schema to ApiResponseSchema
- ProductsApi - Changes after renaming ApiResponse schema
- ProductsApiController - Changes after renaming ApiResponse schema
- ProductsApiDelegate - Changes after renaming ApiResponse schema
- ProductsApiDelegateImpl - Changes after renaming ApiResponse schema
- ProductsMapper - Ignoring some entity attributes in the mapping
- users.yml - Changing ApiResponse schema to ApiResponseSchema
- UsersApi - Changes after renaming ApiResponse schema
- UsersApiDelegate - Changes after renaming ApiResponse schema
- UsersApiDelegateImpl - Changes after renaming ApiResponse schema
- UsersMapper - Ignoring some entity attributes in the mapping
- pom.xml - Update version from 0.0.10-SNAPSHOT to 0.0.11-SNAPSHOT
#### Deleted
- ModelApiResponse - After renaming ApiResponse to ApiResponseSchema, this class is redundant
#### Added
- ApiResponseSchema - [automatically generated by OpenAPI generator]

### [0.0.10-SNAPSHOT] - 2024-08-16 [@stefangolubov](https://github.com/stefangolubov)
#### Changed
- api.yml - Adding orders path and schema configuration
- application.properties - Adding tagsSorter configuration
- Orders - Adding NamedStoredProcedureQuery in Orders entity and change data types for some fields
- pom.xml - Update version from 0.0.9-SNAPSHOT to 0.0.10-SNAPSHOT
#### Added
- NewOrder - [automatically generated by OpenAPI generator]
- NewOrderMapper - Interface for mapping NewOrder model to entity and vice versa
- orders.yml - configuring orders paths
- orders.yml - configuring orders schema
- OrdersApiDelegateImpl - Implementing logic for all API calls 
- OrdersMapper - Interface for mapping Orders model to entity and vice versa
- OrdersRepository - Repository for Orders entity
- OrdersService - Service for Orders API calls
- Orders - model [automatically generated by OpenAPI generator]
- OrdersApi - [automatically generated by OpenAPI generator]
- OrdersApiController - [automatically generated by OpenAPI generator]
- OrdersApiDelegate - [automatically generated by OpenAPI generator]

### [0.0.9-SNAPSHOT] - 2024-08-14 [@stefangolubov](https://github.com/stefangolubov)
#### Changed
- UsersApiDelegateImpl - Make sure that only unique users are retrieved when calling findByUsername method
- ProductsApiDelegateImpl - Make sure that only unique products are retrieved when calling findByName method
- pom.xml - Update version from 0.0.8-SNAPSHOT to 0.0.9-SNAPSHOT

### [0.0.8-SNAPSHOT] - 2024-08-14 [@stefangolubov](https://github.com/stefangolubov)
#### Changed
- changeset-004-create-procedures.sql - Replace output parameter ID with username
- Users - Add NamedStoredProcedureQuery in Users entity
- UsersRepository - Add insertUser method
- UsersService - Implement creation of a user with insert_user stored procedure in addUser method
- pom.xml - Update version from 0.0.7-SNAPSHOT to 0.0.8-SNAPSHOT

### [0.0.7-SNAPSHOT] - 2024-08-13 [@stefangolubov](https://github.com/stefangolubov)
#### Changed
- api.yml - Refactoring ApiResponse, Users and Products schema and paths into separate yml files
- pom.xml - Update version from 0.0.6-SNAPSHOT to 0.0.7-SNAPSHOT
#### Added
- apiresponse.yml - Move Users schema from api.yml to separate yml file
- users.yml - Move Users paths from api.yml to separate yml file
- products.yml - Move Products paths from api.yml to separate yml file

### [0.0.6-SNAPSHOT] - 2024-08-13 [@stefangolubov](https://github.com/stefangolubov)
#### Changed
- api.yml - Refactoring Users and Products schema into separate yml files
- pom.xml - Update version from 0.0.5-SNAPSHOT to 0.0.6-SNAPSHOT
#### Added
- users.yml - Move Users schema from api.yml to separate yml file
- products.yml - Move Products schema from api.yml to separate yml file

### [0.0.5-SNAPSHOT] - 2024-08-13 [@stefangolubov](https://github.com/stefangolubov)
#### Changed
- api.yml - Adding products APIs and schema
- NewUserMapper - Removing redundant INSTANCE variable
- pom.xml - Update version from 0.0.4-SNAPSHOT to 0.0.5-SNAPSHOT
- Products.xml - Change data type for: ID, price and quantity
- ProductsRepository - Adding findByNameIgnoreCase
- UsersApi [automatically generated by OpenAPI generator]
- UsersApiDelegate [automatically generated by OpenAPI generator]
- UsersApiDelegateImpl - Adding nullsafe check for users using StringUtils from apache library
- UserMapper - Removing redundant INSTANCE variable
#### Added
- NewProductsMapper - Interface for mapping NewProducts model to entity and vice versa
- ProductsApiDelegateImpl - Implementation of REST APIs for Products
- ProductsMapper - Interface for mapping Products model to entity and vice versa
- ProductsService - Service for CRUD operations needed for REST APIs
- NewProduct [automatically generated by OpenAPI generator]
- Products [automatically generated by OpenAPI generator]
- ProductsApi [automatically generated by OpenAPI generator]
- ProductsApiController [automatically generated by OpenAPI generator]
- ProductsApiDelegate [automatically generated by OpenAPI generator]

### [0.0.4-SNAPSHOT] - 2024-08-13 [@stefangolubov](https://github.com/stefangolubov)
#### Changed
- api.yml - Adding /users/findAll get method 
- application.properties - Adding operationsSorter
- UsersApiDelegateImpl.java - Adding implementation for findAllUsers
- UsersService.java - Adding findAll
- pom.xml - Adding maven-compiler-plugin.version property and hideGenerationTimestamp attribute in the openapi-generator plugin config options