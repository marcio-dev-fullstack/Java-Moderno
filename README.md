# 🚀 Dev Knowledge AI Hub (v3 - JWT Security Enabled)

Uma API RESTful robusta desenvolvida em **Java 21 LTS** seguindo as **6 Etapas do Guia do Engenheiro Java Moderno**, incorporando **Spring Security com Autenticação Stateless JWT**, **Spring AI (RAG + pgvector)**, **Kafka** e **Docker/CI/CD**.

---

## 🔑 Novidades na Versão v3: Autenticação JWT

- **Registrar usuário**: `POST /api/v1/auth/register` (recebe token JWT)
- **Autenticar usuário**: `POST /api/v1/auth/login` (retorna o Bearer token)
- **Proteção dos Endpoints**:
  - `GET /api/v1/articles` -> Público
  - `POST /api/v1/articles` -> Exige token `Bearer <JWT_TOKEN>` (`ROLE_USER` / `ROLE_ADMIN`)
  - `POST /api/v1/ai/ask` -> Exige token `Bearer <JWT_TOKEN>` (`ROLE_USER` / `ROLE_ADMIN`)
  - `Swagger UI` e `Actuator` -> Públicos para testes e monitoramento.

---

## ⚡ Como Rodar Localmente

```bash
# 1. Subir a infraestrutura (Postgres + pgvector, Redis, Kafka)
docker-compose up -d

# 2. Executar a aplicação Spring Boot 3.3 (Java 21 com Virtual Threads)
./mvnw spring-boot:run
```

Acesse o Swagger com suporte ao botão **Authorize (Bearer Token)**:
`http://localhost:8080/swagger-ui.html`
