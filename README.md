## 🎬 Movie Catalog Searcher

A Spring Boot 3 & Angular application for real-time movie discovery and personalized collections. It integrates with the OMDB API for metadata and features a robust, Auth0-powered identity management system.

---

### 🚀 Features

* **Real-time Discovery**: Fetches titles, genres, and posters directly via OMDB API integration.
* **Modern Auth (OAuth0)**: Secure registration and login using Auth0 (OIDC/OAuth 2.0) for centralized identity management.
* **Session Persistence**: Utilizes Session Storage in the browser to maintain the user's active state and access tokens without constant re-authentication.
* **Optimized Delivery**: Nginx serves the frontend assets and acts as a high-performance web server.
* **Personalized Favorites**: Users can persist their favorite movies to a private catalog.
* **Single Page Application (SPA)**: A fast, responsive, and dark-themed UI built with **Angular** and **Bootstrap 5**.
* **Containerized**: "One-command" deployment using Docker and Docker Compose.

---

### 🏗️ Architecture

The project follows a Decoupled Client-Server pattern with a clear separation of concerns:

* **Frontend (Angular)**: A standalone SPA that handles routing, UI state, and Auth0 integration. It manages user credentials via the Auth0 SDK and stores tokens in Session Storage.

* **Backend (Spring Boot)**: Acts as a Resource Server. It validates JWT tokens issued by Auth0 to secure REST API endpoints.

* **Service Layer**: Encapsulates business logic, including OMDB API communication and favorites management.

* **Repository**: Manages data persistence through Spring Data JPA.

* **Security**: Configured with Spring Security's OAuth2 resource server support to interact with Auth0.

---

### 🛠️ Tech Stack

| Layer | Technology |
| --- | --- |
|**Web Server**| Nginx (Alpine-based)
| **Backend** | Java 17, Spring Boot 3.x, Spring Security (OAuth2/ Auth0) |
| **Frontend** | Angular, SessionStorage, Bootstrap 5, CSS3 |
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

```dockerfile
FROM node:22-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
COPY nginx.conf etc/nginx/nginx.conf
COPY mime.type etc/nginx/mime.type
COPY --from=builder /app/dist/SearchFilmsAngular/browser/ /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

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
      - .SearchFilmsSpring/src/main/resources/create_tables.sql:/docker-entrypoint-initdb.d/create_tables.sql
      - postgres_data:/var/lib/postgresql/data
      
  angular:
     build: SearchFilmsAngular
     container_name: angular_app_sf
     ports:
       - "4200:80"
     depends_on:
       - spring

  spring:
    build: SeachFilmsSpring
    container_name: spring_app_sf
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
      - SPRING_DATASOURCE_USERNAME=${DATABASE_POSTGRES_USER}
      - SPRING_DATASOURCE_PASSWORD=${DATABASE_POSTGRES_PASSWORD}
      - SPRING_API_KEY_OMDB=${SPRING_API_KEY_OMDB}
      - OMDB_API_URL=${OMDB_API_URL}
      - FRONTEND_ANGULAR_URL=${FRONTEND_ANGULAR_URL}
    depends_on:
      - db

volumes:
  postgres_data:

```

#### 2. Enviroment Variables

```.env
### Database ###

DATABASE_POSTGRES_USER= "Database Username"
DATABASE_POSTGRES_PASSWORD= "Database Passowrd"
DATABASE_POSTGRES_DB= "Database Name"

### SpringAPP ###

SPRING_DATASOURCE_URL= "URL to connect with database"
SPRING_API_KEY_OMDB= "Your OMDB API Key"
OMDB_API_URL= "OMDB API url"
FRONTEND_ANGULAR_URL= "Your Angular url"
```

---

**To run the project:**

```bash
docker-compose up --build

```
