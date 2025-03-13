# GitHub Repositories API

## Project Overview
GitHub Repositories API is a **Quarkus-based** application that retrieves repositories for a specified GitHub user, filters out forks, and retrieves their branches. The API supports dynamic query parameters, allowing users to fetch specific types of repositories.

## 🛠️ Technologies Used
- **Java 17** – Modern, high-performance Java version.
- **Quarkus** – Fast, cloud-native Java framework.
- **Mutiny (Uni & Multi)** – Reactive programming.
- **OpenAPI (Swagger UI)** – API documentation.
- **JUnit 5 + REST-Assured** – Integration testing.
- **Maven** – Dependency management and build system.

---

## How to Run the Project?

### 1️ **Clone the Repository**
```bash
git clone https://github.com/Malek679/github-repo-task
```



### 2 **Build and Run the Application**
```bash
./mvnw quarkus:dev
```

## API Documentation (Swagger UI)
To access the interactive API documentation, start the application and go to:
localhost:8080/q/swagger-ui
