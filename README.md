## 🎬 Movie Catalog Searcher

A comprehensive Spring Boot 3 application for real-time movie discovery and personalized collections. It integrates with the **OMDB API** for metadata and features a secure, **JWT-based** authentication system.

---

### 🚀 Features

* **Real-time Discovery**: Fetches titles, genres, and posters directly via OMDB API integration.
* **User Management**: Secure registration, login, and session handling using **JWT**.
* **Personalized Favorites**: Users can persist their favorite movies to a private catalog.
* **Responsive UI**: A modern, dark-themed catalog built with **Bootstrap 5** and **Thymeleaf**.
* **Containerized**: "One-command" deployment using Docker and Docker Compose.

---

### 🏗️ Architecture

The project implements a robust **MVC (Model-View-Controller)** pattern with a clear separation of concerns:

* **Controller**: Handles routing for movies and authentication (e.g., `LoginController`, `MovieController`).
* **Services**: Encapsulates business logic, API calls, and security processing.
* **Repository**: Manages data persistence through Spring Data JPA interfaces.
* **Entity**: Defines the database schema for Users, Movies, and Favorites.
* **Security**: Custom JWT implementation for stateless authentication.

---

### 🛠️ Tech Stack

| Layer | Technology |
| --- | --- |
| **Backend** | Java 17, Spring Boot 3.x, Spring Security (JWT) |
| **Frontend** | Thymeleaf, Bootstrap 5, CSS3 |
| **Database** | PostgreSQL |
| **DevOps** | Docker, Docker Compose |

---

### 🐳 Docker Deployment

#### 1. Dockerfile

Multi-stage build to ensure a small, production-ready image.

```dockerfile
FROM maven:3.8.5-openjdk-17 AS build
COPY SeachFilmsSpring .
RUN mvn clean package -DskipTests

FROM clipse-temurin:17-jdk-jammy
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]

```

#### 2. Docker Compose

Orchestrates the application and the PostgreSQL database.

```yaml
services:
  db:
    image: postgres:15-alpine
    container_name: postgres_db
    environment:
      POSTGRES_USER: ${DATABASE_POSTGRES_USER}
      POSTGRES_PASSWORD: ${DATABASE_POSTGRES_PASSWORD}
      POSTGRES_DB: ${DATABASE_POSTGRES_DB}
    ports:
      - "5432:5432"
    volumes:
      - ./src/main/resources/create_tables.sql:/docker-entrypoint-initdb.d/create_tables.sql
      - postgres_data:/var/lib/postgresql/data

  app:
    build: .
    container_name: spring_app
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
      - SPRING_DATASOURCE_USERNAME=${DATABASE_POSTGRES_USER}
      - SPRING_DATASOURCE_PASSWORD=${DATABASE_POSTGRES_PASSWORD}
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
      - OMDB-API-KEY=${SPRING_API_KEY_OMDB}
    depends_on:
      - db

volumes:
  postgres_data:

```

---

### 🔑 Authentication Flow (JWT)

1. **Sign Up/Login**: User submits credentials via the `userauth` templates.
2. **Token Issuance**: Upon validation, the server generates a signed JWT.
3. **Authorized Access**: The token is sent in headers for subsequent requests to access protected routes like `/favorites`.

**To run the project:**

```bash
docker-compose up --build

```
