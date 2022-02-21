# Card Transaction

## Description

This api is a sample project to help transaction routines

# Requirements

## Java 11 JDK
Download a copy of the OpenJDK Java 11 JDK from [here](https://adoptopenjdk.net/index.html?variant=openjdk11&jvmVariant=hotspot)

## Maven 3
Download the [latest version of maven](https://maven.apache.org/download.cgi) and follow the installation instructions.

## Docker / docker-compose
Before running the application, you must start PostgreSql as the database.

A `docker-compose` file is provided for this purpose. To start database, run the following:
```
docker-compose up -d
```

For MacOS and Windows you can use [Docker Desktop](https://www.docker.com/products/docker-desktop).

### PostgreSql configuration 
The `docker-compose` file has the following properties for PostgreSql configuration:
 - username: postgres
 - password: postgres
 - database name: card_transaction

# Building the project
Compile
```
mvn compile
```

Run unit tests
```
mvn test
```

## Running the application
To run the application, execute the following code:

```
mvn spring-boot:run
```

## Accessing the application
After successfully running the application, the default endpoint will be available:

http://localhost:8080/swagger-ui/index.html#/


