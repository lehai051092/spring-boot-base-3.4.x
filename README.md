## Env info
JDK 21
Postgres 16.4

## Run migration
`mvn liquibase:update`

## Swagger
http://localhost:8080/swagger-ui/index.html

## Run docker
1. `docker-compose up --build`
2. `docker ps`
3. `docker logs <container_id>`

## Feature list
- [x] Database Integration (PostgreSQL, JPA/Hibernate)
- [x] Authentication & Authorization (JWT)
- [x] Error Handling & Exception Management
- [x] Validation (Request Parameters, DTO, Query)
- [x] API Documentation (Swagger Integration)
- [x] Caching (Redis)
- [x] Logger (SLF4J & Logback)
- [x] Docker Setup
- [x] Unit Test & Integration Test (JUnit, Mockito)
- [x] Forget Password Feature
- [ ] Multi-language Support (i18n)
- [ ] CI/CD Integration
- [ ] Role-Based Access Control (RBAC)
- [ ] Request/Response Logging
- [ ] Health Check Endpoint
- [ ] CSRF Protection (Spring Security)
- [ ] Asynchronous Task Handling (@Async)
- [ ] Deployment Configuration (Environment Profiles)
- [ ] API Versioning
