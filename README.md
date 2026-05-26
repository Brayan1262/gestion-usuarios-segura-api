# Secure User Management API

API REST segura para gestión de usuarios, autenticación, autorización por roles y protección de endpoints mediante JWT.

## Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Security
- JWT
- PostgreSQL
- Docker
- Spring Data JPA
- Hibernate
- Lombok
- Validation
- Swagger/OpenAPI
- JUnit 5
- Mockito
- Maven

## Funcionalidades

- Registro de usuarios
- Login con JWT
- Generación de token Bearer
- Protección de rutas
- Roles ADMIN, USER y SUPPORT
- CRUD de usuarios
- Activar y desactivar usuarios
- Validaciones
- Manejo global de excepciones
- Documentación Swagger
- Tests unitarios

## Arquitectura del proyecto

El proyecto está organizado en los siguientes paquetes principales:

- `config`
- `controller`
- `dto`
- `entity`
- `exception`
- `repository`
- `security`
- `service`

## Endpoints principales

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/users`
- `GET /api/users/{id}`
- `GET /api/users/active`
- `PUT /api/users/{id}`
- `PATCH /api/users/{id}/disable`
- `PATCH /api/users/{id}/enable`
- `GET /api/admin/dashboard`
- `GET /api/support/panel`
- `GET /api/test`

## Cómo ejecutar el proyecto

1. Clonar el repositorio:

```bash
git clone <repository-url>
cd secure-user-management-api
```

2. Levantar PostgreSQL con Docker:

```bash
docker compose up -d
```

3. Ejecutar Spring Boot:

```bash
./mvnw spring-boot:run
```

4. En Windows:

```powershell
.\mvnw spring-boot:run
```

## Configuración de base de datos

Asegúrate de tener PostgreSQL configurado con los siguientes parámetros:

- database: `secure_users_db`
- user: `postgres`
- password: `postgres`
- port: `5432`

## Cómo probar login

1. Registrar un usuario:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user1",
    "email": "user1@example.com",
    "password": "Password123",
    "role": "USER"
  }'
```

2. Iniciar sesión:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user1",
    "password": "Password123"
  }'
```

La respuesta incluirá un token JWT que se debe usar en las solicitudes protegidas.

## Cómo usar el token

Incluye el token en el encabezado `Authorization` de tus peticiones:

```http
Authorization: Bearer TOKEN
```

## Swagger

La documentación Swagger está disponible en:

- `http://localhost:8080/swagger-ui/index.html`

## Tests

Ejecutar pruebas unitarias con:

```bash
./mvnw test
```

Ejecutar build completo con pruebas:

```bash
./mvnw clean install
```

## Autor

- Brayan Jair Chavez Oscor
- GitHub: https://github.com/Brayan1262
- LinkedIn: https://www.linkedin.com/in/brayan-chavez-218088334/
- Portafolio: https://brayan1262.github.io/portafolio-brayan/

## Futuras mejoras

- Refresh token
- Recuperación de contraseña
- Auditoría
- Logs
- Dockerización completa de la app + base de datos
- Deploy
