# CO2 Tracker

CO2 Tracker is a Spring Boot application designed to track CO2 readings in various districts of different cities. The application uses MySQL as the database and provides a Swagger UI for easy testing of the REST APIs.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java 21**: The application is built using Java 21. Make sure you have it installed.
- **Maven**: Maven is used for managing dependencies and building the project.
- **MySQL**: MySQL is required for the database. Ensure you have MySQL installed and running.

## Setting Up the Database

1. **Install MySQL**: If you don't have MySQL installed, download and install it from the official website: [MySQL Downloads](https://dev.mysql.com/downloads/).
2. **Create Database**: Create a database for the project. You can do this using the MySQL command line or any MySQL GUI tool like MySQL Workbench.

```sql

-- Create Database
CREATE DATABASE co2tracker;

-- Insert cities
INSERT INTO CITY (name) VALUES ('Barcelona');
INSERT INTO CITY (name) VALUES ('Wien');
INSERT INTO CITY (name) VALUES ('Munchen');

-- Insert districts
INSERT INTO DISTRICT (name, city_id) VALUES ('Gràcia', 1);
INSERT INTO DISTRICT (name, city_id) VALUES ('Eixample', 1);
INSERT INTO DISTRICT (name, city_id) VALUES ('Währing', 2);
INSERT INTO DISTRICT (name, city_id) VALUES ('Penzing', 2);
INSERT INTO DISTRICT (name, city_id) VALUES ('Maxvorstadt', 3);
```

## Configure Database Connection

Update the application.yaml file with your MySQL database credentials.

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/co2tracker
    username: **your_username**
    password: **your_password**
  jpa:
    hibernate:
      ddl-auto: **update**

## Swagger
The application includes Swagger for API documentation and testing. Once the application is running, you can access the Swagger UI at the following URL:

[Swagger] (http://localhost:8080/swagger-ui.html)

