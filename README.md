# 🚀 Dev Knowledge AI Hub (`dev-knowledge-ai-hub`)

> **Projeto Exemplo Integrando as 6 Etapas do Guia do Engenheiro Java Moderno**

API backend desenvolvida em **Java 21 (LTS)** com **Spring Boot 3**, **Spring AI (RAG & Tool Calling)**, **PostgreSQL (pgvector)**, **Redis**, **Kafka**, **Docker/Kubernetes** e **CI/CD no GitHub Actions**.

---

## 📌 Visão Geral das 6 Etapas

1. **Core Java (Java Essencial)**: Uso de **Java 21**, **Virtual Threads (Loom)** para altíssima concorrência, imutabilidade com **Records**, **Pattern Matching** e **Streams API**.
2. **Backend com Spring**: **Spring Boot 3.3**, REST APIs padronizadas, **Spring Data JPA** com Hibernate e **Spring Security** stateless via **JWT**.
3. **Dados & Mensageria**: Banco de dados **PostgreSQL** com suporte a **pgvector** para embeddings de IA, cache de alta velocidade com **Redis** e mensageria assíncrona orientada a eventos com **Apache Kafka**.
4. **Cloud & DevOps**: empacotamento **Docker** com build multi-stage, orquestração local com `docker-compose`, manifestos para **Kubernetes** (`k8s/`) e esteira **CI/CD** no **GitHub Actions** (`.github/workflows/ci.yml`).
5. **IA em Java**: Integração nativa com **Spring AI**, **RAG (Retrieval-Augmented Generation)** com busca vetorial no pgvector e **Tool Calling (MCP)** permitindo que a IA invoque serviços Java.
6. **Excelência em Produção**: Suíte de testes com **JUnit 5**, **Mockito** e **Testcontainers** (subindo containers reais de Postgres e Kafka durante a integração), observabilidade completa com **Actuator + Micrometer + Prometheus**.

---

## 🛠️ Como Executar Localmente

### Pré-requisitos
* **Java 21** ou superior instalados
* **Maven 3.9+**
* **Docker** e **Docker Compose**

### 1. Iniciar a Infraestrutura (Postgres + pgvector, Redis, Kafka)
```bash
docker-compose up -d
```

### 2. Compilar e Executar a Aplicação Java
```bash
mvn clean package
java -jar target/dev-knowledge-ai-hub-1.0.0-SNAPSHOT.jar
```
*A aplicação estará rodando em:* `http://localhost:8080`

---

## 📡 Endpoints Principais (API REST)

| Método | Endpoint | Descrição | Estágio do Guia |
|---|---|---|---|
| `POST` | `/api/v1/articles` | Cadastra um novo artigo técnico e dispara evento no Kafka | Backend Spring + Kafka |
| `GET` | `/api/v1/articles` | Lista artigos usando Java 21 Streams e Records | Core Java 21 |
| `POST` | `/api/v1/ai/ask` | Consulta o assistente de IA via RAG e Tool Calling | IA em Java (Spring AI) |
| `GET` | `/actuator/health` | Status de saúde da aplicação e métricas | Produção & Observabilidade |

---

## 🧪 Executando Testes de Integração (Testcontainers)

```bash
mvn test
```

---

## 📄 Licença
Distribuído sob a licença MIT. Sinta-se à vontade para utilizar como base de estudo ou template no GitHub!
