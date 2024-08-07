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
INSERT INTO CITY (name) VALUES ('BARCELONA');
INSERT INTO CITY (name) VALUES ('WIEN');
INSERT INTO CITY (name) VALUES ('MUNCHEN');

-- Insert districts
INSERT INTO DISTRICT (name, city_id) VALUES ('Gràcia', 1);
INSERT INTO DISTRICT (name, city_id) VALUES ('Eixample', 1);
INSERT INTO DISTRICT (name, city_id) VALUES ('Währing', 2);
INSERT INTO DISTRICT (name, city_id) VALUES ('Penzing', 2);
INSERT INTO DISTRICT (name, city_id) VALUES ('Maxvorstadt', 3);
```

## Configure Database Connection

Update the application.yaml file with your MySQL database credentials and to chage the auto DDL 
from create-drop to update after the first execution.

spring.datasource.url=jdbc:mysql://localhost:3306/co2tracker 
spring.datasource.username=**your_username** 
spring.datasource.password=**your_password** 
spring.jpa.hibernate.ddl-auto=**create-drop** 

## Swagger
The application includes Swagger for API documentation and testing. Once the application is running, you can access the Swagger UI at the following URL:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Authentication

### Type of Authentication
The application uses Spring Security to manage authentication and authorization. Specifically, it is configured to use HTTP Basic authentication, which requires users to provide a username and password to access protected resources.

### Predefined Users
To facilitate testing and usage of the API, three predefined users have been created with the following details:

- **Barcelona**
  - Username: `BarcelonaCityHall`
  - Password: `passwordBarcelona`
  - Role: `BARCELONA`

- **Wien**
  - Username: `WienCityHall`
  - Password: `passwordWien`
  - Role: `WIEN`

- **Munchen**
  - Username: `MunchenCityHall`
  - Password: `passwordMunchen`
  - Role: `MUNCHEN`

### Credential Management
User credentials are managed in memory using `InMemoryUserDetailsManager`.
