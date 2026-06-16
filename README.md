# Secure User Management API 🛡️

> API REST segura de nivel empresarial diseñada para la gestión avanzada de usuarios, autenticación y control de acceso basado en roles (RBAC), protegiendo endpoints de misión crítica mediante JSON Web Tokens (JWT).
>
> ![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=java&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-316192?style=for-the-badge&logo=postgresql&logoColor=white) ![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white) ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

---

## 🚀 Características Principales

- **Gestión Avanzada de Usuarios (RBAC):** Autenticación robusta y control de acceso con roles jerárquicos (`ADMIN`, `USER`, `SUPPORT`), asegurando que cada usuario acceda solo a los recursos permitidos.
- **Seguridad y Protección Stateless:** Filtros personalizados con Spring Security para validar y decodificar JWT en cada petición HTTP, garantizando comunicaciones seguras sin estado.
- **Operaciones CRUD Completas:** Registro, inicio de sesión, activación, desactivación (soft delete) y administración integral del ciclo de vida de los usuarios en la plataforma.
- **Manejo Global de Excepciones:** Respuestas estandarizadas y amigables ante errores, validaciones fallidas o problemas de seguridad a través de un `@ControllerAdvice`.
- **Documentación Interactiva:** Integración nativa con OpenAPI (Swagger UI) para la visualización, exploración y prueba de todos los endpoints directamente desde el navegador.

---

## 🛠️ Stack Tecnológico

| Capa | Tecnología |
| :--- | :--- |
| **Backend REST Core** | Java 21, Spring Boot 3, Spring Web |
| **Capa de Seguridad** | Spring Security, JJWT (JSON Web Token) |
| **Persistencia de Datos** | Spring Data JPA, Hibernate, PostgreSQL 15 |
| **Pruebas y Utilidades** | JUnit 5, Mockito, Lombok, Validation |
| **Documentación API** | Springdoc OpenAPI (Swagger UI) |
| **Orquestación DevOps** | Docker, Docker Compose, Maven |

---

## ⚙️ Cómo ejecutar el proyecto (Modo Local)

La infraestructura de base de datos está contenerizada para garantizar un despliegue rápido y sin conflictos de configuración.

### Prerrequisitos
1. Tener instalado [Docker Desktop](https://www.docker.com/products/docker-desktop/).
2. Tener instalado [Java 21](https://adoptium.net/) en tu máquina anfitriona.

### Paso 1: Levantar la Infraestructura Core
Abre una terminal en la raíz del proyecto y levanta el servicio de PostgreSQL utilizando el orquestador:

```bash
docker-compose up -d
```
> Esto descargará la imagen de PostgreSQL 15 y levantará la base de datos `secure_users_db` en el puerto 5432.

### Paso 2: Ejecutar la Aplicación Spring Boot
Inicia el servidor backend ejecutando el wrapper de Maven (no requiere instalación global de Maven):

**En Windows (PowerShell):**
```powershell
.\mvnw spring-boot:run
```

**En Linux / macOS:**
```bash
./mvnw spring-boot:run
```

### Paso 3: Explorar el Centro de Mando API
1. Abre tu navegador web y dirígete a: **http://localhost:8080/swagger-ui/index.html**
2. Puedes interactuar con la interfaz Swagger para registrar nuevos usuarios, obtener tokens JWT y probar endpoints protegidos.

---

## 🔐 Flujo de Autenticación Rápido

**1. Registrar un nuevo usuario:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "adminUser",
    "email": "admin@example.com",
    "password": "Password123",
    "role": "ADMIN"
  }'
```

**2. Iniciar Sesión (Generar Token):**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "adminUser",
    "password": "Password123"
  }'
```
> La API responderá con un token JWT firmado. Guárdalo para el siguiente paso.

**3. Consumir Endpoint Protegido:**
Incluye el token obtenido en el encabezado `Authorization` para cualquier petición segura:
```http
Authorization: Bearer <TU_TOKEN_JWT>
```

---

## 📂 Estructura del Proyecto

*   `/config` - Configuraciones generales (Seguridad, CORS, Swagger).
*   `/controller` - Exposición de endpoints REST (API Gateway local).
*   `/dto` - Objetos de Transferencia de Datos (Data Transfer Objects).
*   `/entity` - Modelos de dominio mapeados a la base de datos relacional.
*   `/exception` - Control centralizado y manejo de errores personalizados.
*   `/repository` - Interfaces de persistencia (Spring Data JPA).
*   `/security` - Lógica core de seguridad, Provider, filtros JWT y configuración.
*   `/service` - Lógica de negocio y validaciones de aplicación.

---

## 🚀 Roadmap y Futuras Mejoras

- [ ] Implementación de **Refresh Tokens** para mantener sesiones activas de forma segura.
- [ ] Flujo de **Recuperación de Contraseña** mediante envío asíncrono de correos electrónicos.
- [ ] **Auditoría de Acciones (Logs)** para trazabilidad en endpoints de administración.
- [ ] Dockerización del Backend para construir una red completa en Docker Compose.
- [ ] Pipelines de CI/CD para testing y despliegue automatizado.

---

## 👨‍💻 Autor

**Brayan Jair Chavez Oscor**
*Ingeniería de Software / Arquitectura Backend*

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Brayan1262)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/brayan-chavez-218088334/)
[![Portfolio](https://img.shields.io/badge/Portfolio-000000?style=for-the-badge&logo=web&logoColor=white)](https://brayan1262.github.io/portafolio-brayan/)

> *Desarrollado como demostración técnica de buenas prácticas en arquitectura de software seguro y APIs empresariales.*
