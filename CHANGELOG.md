# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.0.4-SNAPSHOT] - 2024-08-13 [@stefangolubov](https://github.com/stefangolubov)
## Changed
- api.yml - Adding /users/findAll get method 
- application.properties - Adding operationsSorter
- UsersApiDelegateImpl.java - Adding implementation for findAllUsers
- UsersService.java - Adding findAll
- pom.xml - Adding maven-compiler-plugin.version property and hideGenerationTimestamp attribute in the openapi-generator plugin config options