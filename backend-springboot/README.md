# Ticket Corner - Backend

Welcome to **Ticket Corner**, a movie ticket booking system that allows customers to book tickets for movies, vendors to manage the available tickets, and administrators to manage users and roles.

## Project Description

**Ticket Corner** is a backend service for a movie ticket booking platform. It manages users (customers, vendors, and admins), movie information, ticket availability, and facilitates the booking process. The system supports various features, including ticket pool management, role-based access control, and multi-threaded processing for both customer and vendor operations.

## Features

- **User Management**: Handle different user roles such as **Customer**, **Vendor**, and **Admin**.
- **Movie Management**: Add, update, and retrieve movie details.
- **Ticket Pool Management**: Vendors can add tickets to the pool, while customers can purchase tickets from the pool.
- **Multi-threading**: Customers and vendors interact with the system using separate threads to simulate concurrent ticket purchasing and ticket addition.
- **Security**: Role-based access control using Spring Security.
- **Database**: Uses MySQL for data persistence.

## Tech Stack

- **Spring Boot**: Java framework for building the backend service.
- **Spring Security**: For authentication and authorization.
- **JPA/Hibernate**: For database interactions.
- **MySQL**: For persistent data storage.
- **Slf4j**: For logging.
- **Maven**: For dependency  and build automation.

## Requirements

- **Java 17 or later**
- **MySQL 8.0 or later**
- **Maven 3.x**
- **IDE**: IntelliJ IDEA, Eclipse, or any preferred Java IDE

## Getting Started

### Clone the repository

Clone the repository to your local machine using the following command:

```bash
git clone https://github.com/dilruk25/movie-ticket-booking-system.git
cd backend-springboot
```

### Configuration

#### 1. **Database Setup**

Ensure you have a MySQL database running. Create a database called `ticket_corner_db` and configure the connection in `application.properties` or `application.yml`.

Example `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ticket_corner_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

#### 2. **Build and Run**

To build the application, use Maven:

```bash
mvn clean install
```

To run the application locally, use the following command:

```bash
mvn spring-boot:run
```

Alternatively, you can run the `.jar` file:

```bash
java -jar target/ticket-corner-backend-0.0.1-SNAPSHOT.jar
```

### API Endpoints

#### 1. **Authentication**

- **POST /login**: Authenticates a user and returns a JWT token.
    - Request body:
      ```json
      {
        "email": "user@example.com",
        "password": "password"
      }
      ```

#### 2. **User Management** (Accessible by Admin)

- **GET /users**: Retrieve all users (Customers, Vendors, and Admins).
- **GET /users/{userId}**: Retrieve a specific user by ID.
- **POST /users**: Create a new user.
- **PUT /users/{userId}**: Update a user's details.
- **DELETE /users/{userId}**: Delete a user.

#### 3. **Movie Management** (Accessible by Vendor/Admin)

- **GET /movies**: Retrieve all movies.
- **POST /movies**: Add a new movie.
- **PUT /movies/{movieId}**: Update movie details.
- **DELETE /movies/{movieId}**: Delete a movie.

#### 4. **Ticket Pool Management** (Accessible by Vendor)

- **POST /tickets**: Add tickets to the pool.
- **GET /tickets**: View available tickets.
- **POST /tickets/purchase**: Purchase a ticket (Customer action).

#### 5. **Admin Operations**

- **GET /admin/stats**: Get statistics about ticket sales, available tickets, and users.

### Security

- **Roles**:
    - `ROLE_ADMIN`: Can manage all users, movies, and tickets.
    - `ROLE_VENDOR`: Can add tickets to the pool and manage movies.
    - `ROLE_CUSTOMER`: Can view movies and purchase tickets.

### Logging

The application uses **Slf4j** for logging. Logs can be found in the console, and a more detailed log file can be configured based on your preferences.

### Testing

To run tests, use Maven:

```bash
mvn test
```