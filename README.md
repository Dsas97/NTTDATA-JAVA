# NTTDATA Manage accounts

## Description
This project consists of two microservices developed with Spring Boot for customer account management. The child microservices:

1. **person-service**: Manages the creation of the client entity with its respective data.
2. **movement-service**: Manages the creation of a client's accounts and the movements made in them, in addition to generating the account statement.

Both microservices are configured to use a SQL database and are ready to run in Docker containers or locally.

## Project structure
management-accounts-ntttdata/

├── person-service/

│ ├── src/

│ ├── build/

│ ├── Docker file

│ └── ...

├── service-movement/

│ ├── src/

│ ├── build/

│ ├── Docker file

│ └── ...

└── Manage accounts.postman_collection.json

|__ docker-compose.yml    

├── Manage accounts.postman_collection.json

└──Database.sql

##Prerequisites
-Java 21
- coupler
- SQL database (SQL Server, Mysql, MariaDB, postgress, etc.)
- Mailman


## Facility

### Clone the repository
git clone https://github.com/Dsas97/NTTDATA-JAVA.git

### Database configuration
Modify the application.properties files in both microservices (person-service and motion-service) to point to your database instance.

### Application.properties example

spring.application.name=movement-service

server.port=8082

spring.datasource.url=jdbc:postgresql://localhost:5432/manage_accounts
spring.datasource.username = postgres
spring.datasource.password=admin

### To create the data base
Use the SQL script provided in Database/schema.sql to create the database structure and data.sql to populate it with initial data.

## Run in local mode

### Run the microservices

To run each microservice locally, navigate to the microservice directory and use the following command:

java -jar build/libs/person-service-0.0.1-SNAPSHOT.jar

java -jar build/libs/movement-service-0.0.1-SNAPSHOT.jar

## Run in Docker


### Run the containers
First you must execute the commands in the directory of each microservice:

gradle clean 

gradle construction

To run microservices in Docker containers, use the following command from the project root:

docker-compose up --build
