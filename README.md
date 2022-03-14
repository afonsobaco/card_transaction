# Card Transaction

## Description

This api is a sample project to help transaction routines

# Requirements

## Java 11 JDK
Download a copy of the OpenJDK Java 11 JDK from [here](https://adoptopenjdk.net/index.html?variant=openjdk11&jvmVariant=hotspot)

## Maven 3
Download the [latest version of maven](https://maven.apache.org/download.cgi) and follow the installation instructions.

### Compile
```
mvn compile
```

### Run unit tests
```
mvn test
```

## Docker / docker-compose

A `docker-compose` file is provided with the application and the database (Postgres). 


> **Note:** if you already ran the docker-compose for this application before, it is a good practice
> to stop the containers with `docker-compose down` and clean your containers before running the application again.


### Build
```
docker-compose build
```
### Start
```
docker-compose up -d
```

> **Note:** the first time starting the containers, it will download the Postgres image that can take a couple of minutes.

### Accessing the application
After successfully running the containers, the default endpoint will be available:

http://localhost:8080/swagger-ui/index.html#/

### PostgreSql configuration 
The `docker-compose` file has the following environment variables for PostgreSql configuration:
- POSTGRES_USER=postgres
- POSTGRES_PASSWORD=postgres
- POSTGRES_DB=card_transaction

The default port to access postgres database is `5432`




## Building the project locally (with _local_ profile)

If the environment already has an instance of Postgres (or another database) running, it is possible to run the application locally pointing to that database, using spring boot.

### Step 1: application-local.properties
change the `application-local.properties` file to reflect the database host and port:

```
spring.datasource.url=jdbc:postgresql://<host>:<port>/card_transaction
```

### Step 2: Running the application with local profile
To run the application, execute the following code:

```
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

For **Powershell** users:
```
mvn spring-boot:run "-Dspring-boot.run.profiles=local"
```

### Step 3: Accessing the application
Again, after successfully running the application, the default endpoint will be available:

http://localhost:8080/swagger-ui/index.html#/

## Endpoints
- **GET _/account/{accountId}_** - get the account with the given ID.
- **POST _/account_** - create/update account with the given properties.
- **POST _/transaction_** - create a transaction with the given properties.