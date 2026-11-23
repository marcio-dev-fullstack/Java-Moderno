# Dev Knowledge AI Hub (v5 - Versão Final Completa)

> Aplicação Backend Enterprise baseada em **O Guia do Engenheiro Java Moderno**, integrando Java 21 LTS, Virtual Threads, Spring Boot 3.3, Segurança JWT, Cache com Redis, Spring AI com RAG e Vector DB (PostgreSQL pgvector), Mensageria Kafka, Ollama local, CI/CD, Kubernetes, GitHub Codespaces e Observabilidade com Prometheus + Grafana.

---

## 🚀 Arquitetura & Tecnologias Integradas

- **Etapa 01 - Core Java**: Java 21 LTS, Virtual Threads (Project Loom), Records, Streams API.
- **Etapa 02 - Backend Spring**: Spring Boot 3.3, Spring Data JPA, Spring Security com JWT Stateless, REST APIs e Swagger UI.
- **Etapa 03 - Dados & Mensageria**: PostgreSQL 16 com `pgvector`, Redis 7 para Caching em Memória e Apache Kafka (Event-Driven Architecture).
- **Etapa 04 - Cloud & DevOps**: Docker Multi-Stage, Docker Compose, Kubernetes Manifests, Terraform (AWS RDS + ECR), GitHub Actions (AWS/Azure) e GitHub Codespaces (`.devcontainer`).
- **Etapa 05 - IA em Java**: Spring AI, RAG (Retrieval-Augmented Generation), busca por similaridade vetorial, Ollama local (`llama3.2` + `nomic-embed-text`) e Tool Calling (`@Tool`).
- **Etapa 06 - Excelência em Produção**: Testes de integração com JUnit 5 + Testcontainers (Postgres), Criptografia BCrypt, Spring Actuator, Prometheus (porta `9090`) e Grafana (porta `3000`).

---

## ⚡ Como Rodar o Projeto

### 1. Inicializar a Infraestrutura Completa via Docker Compose
```bash
docker-compose up -d
```

### 2. Executar a Aplicação Spring Boot
```bash
./mvnw spring-boot:run
```

---

## 🌐 Endpoints & Dashboards Principais

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **Métricas Prometheus**: `http://localhost:8080/actuator/prometheus`
- **Painel Prometheus**: `http://localhost:9090`
- **Dashboards Grafana**: `http://localhost:3000` (Usuário: `admin` / Senha: `admin`)
- **Autenticação JWT**: `POST http://localhost:8080/api/v1/auth/register`
- **Consulta RAG / IA**: `POST http://localhost:8080/api/v1/ai/ask`
