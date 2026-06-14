# Kipu Backend API

**Kipu** is an open-source Construction Management Platform designed to streamline project planning, tracking, and collaboration for construction teams. This repository contains the backend services and APIs that power the platform.

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Usage](#-usage)
- [Development](#-development)
- [Documentation](#-documentation)
- [License](#-license)
- [Contributing](#-contributing)

## ✨ Features

### Core Modules
- **Project Management** - Create, manage, and track construction projects
- **Task Management** - Assign, monitor, and complete project tasks
- **User Authentication** - Secure login and role-based access control
- **Document Management** - Upload, organize, and share project documents

### Key Capabilities
- 🔄 **RESTful API** - Clean, versioned endpoints for frontend integration
- 🔒 **JWT Authentication** - Secure token-based authentication
- 💾 **Database Integration** - Seamless persistence with PostgreSQL
- 🧪 **Testing** - Comprehensive unit and integration tests
- 📚 **Documentation** - Auto-generated OpenAPI/Swagger documentation

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 4.0.6
- **Language**: Java 21
- **Build Tool**: Maven 3.9.7

### Persistence
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA
- **Driver**: PostgreSQL JDBC Driver

### Security
- **Authentication**: Spring Security
- **Authorization**: JWT (JSON Web Tokens)
- **Password Hashing**: BCryptPasswordEncoder

### Documentation
- **API Docs**: Springdoc OpenAPI (Swagger UI)
- **Format**: OpenAPI 3.0

### Testing
- **Testing Framework**: JUnit 5
- **Test Data**: H2 Database (in-memory)
- **Mocking**: Spring Boot Test, Mockito (optional)

### Additional Libraries
- **Pluralization**: pluralize (for pluralizing table names)
- **Validation**: Jakarta Bean Validation
- **Utilities**: Lombok

## 🔧 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 21+** - Download from [OpenJDK](https://openjdk.org/)
- **Maven 3.6+** - Download from [Apache Maven](https://maven.apache.org/)
- **PostgreSQL 12+** - Download from [PostgreSQL](https://www.postgresql.org/download/)
- **Git** - Download from [Git](https://git-scm.com/downloads/)

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/backend-kipu.git
   cd backend
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```
   This will compile the code, run tests, and package the application into a JAR file.

## config

Database configuration is managed through `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/kipu
spring.datasource.username=kipu_user
spring.datasource.password=kipu_password
spring.datasource.driver-class-name=org.postgresql.Driver

# DDL Auto - change to 'update' for production
spring.jpa.hibernate.ddl-auto=create-drop

# Show SQL queries
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> ⚠️ **Note**: `create-drop` is used for development. For production, use `update` or `validate`.

## 🏁 Usage

### Start the server

After building the project, you can start the application:

```bash
# Using Maven
.\mvnw spring-boot:run

# Or using the generated JAR
java -jar target/backend-*.jar
```

The server will start on port 8080 by default.

### API Documentation

Access the interactive API documentation at:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🔄 Development

### Development with PostgreSQL

To use a persistent PostgreSQL database instead of the in-memory H2 database:

1. **Create database**
   ```sql
   CREATE DATABASE kipu;
   ```

2. **Update `application.properties`**
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/kipu
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

### Running Tests

Run all tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=com.kipu.backend.project.application.service.ProjectServiceTest
```

## 📝 API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token
- `GET /api/auth/me` - Get current user profile

### Projects
- `POST /api/projects` - Create a new project
- `GET /api/projects` - List all projects
- `GET /api/projects/{id}` - Get project details
- `PUT /api/projects/{id}` - Update project
- `DELETE /api/projects/{id}` - Delete project

### Tasks
- `POST /api/projects/{projectId}/tasks` - Create task in a project
- `GET /api/projects/{projectId}/tasks` - List tasks in a project
- `GET /api/tasks/{taskId}` - Get task details
- `PUT /api/tasks/{taskId}` - Update task
- `DELETE /api/tasks/{taskId}` - Delete task

## 📚 Documentation

For detailed API documentation, including request/response formats and examples, see:

- [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- [OpenAPI Specification](http://localhost:8080/v3/api-docs)

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! Here's how you can contribute:

1. **Fork** the repository
2. Create a **feature branch** (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. Open a **Pull Request**

### Contribution Guidelines
- Follow the **code style** used in the project
- Write **meaningful commit messages**
- Keep **pull requests small and focused**
- Update **documentation** where necessary

## 🙏 Acknowledgments

- Built with ❤️ using Spring Boot and PostgreSQL
- Inspired by modern construction management tools

---