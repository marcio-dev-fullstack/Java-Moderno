# 🚀 Dev Knowledge AI Hub

Uma aplicação backend Java 21 moderna que integra **Spring AI (Ollama Local)**, **RAG com PostgreSQL (pgvector)**, **Apache Kafka**, **Autenticação JWT** e esteiras de **CI/CD para GitHub Actions, AWS e Azure**.

---

## 🛠️ Tecnologias Utilizadas

1. **Java 21 LTS** com Virtual Threads habilitadas.
2. **Spring Boot 3.3.2** (Web, JPA, Security, Actuator).
3. **Autenticação Stateless JWT** (HMAC-SHA256).
4. **Spring AI & Ollama** (`llama3.2` para Chat e `nomic-embed-text` para Busca Vetorial).
5. **PostgreSQL 16 + pgvector** para persistência e Vector Store.
6. **Apache Kafka + Zookeeper** para publicação de eventos assíncronos.
7. **Docker & Docker Compose** para containerização total.
8. **GitHub Codespaces** (`.devcontainer`) pronto para execução em 1 clique na nuvem.
9. **Terraform & GitHub Actions** para CI/CD automático na AWS (ECR/ECS) e Azure.

---

## 🚀 Como Executar no GitHub Codespaces (100% na Nuvem com Ollama)

1. No seu repositório do GitHub, clique no botão **Code** > aba **Codespaces** > **Create codespace on main**.
2. O GitHub criará automaticamente o ambiente completo com Java 21, Docker Compose, Postgres (pgvector), Kafka e Ollama.
3. O script `.devcontainer/post-create.sh` baixará os modelos de IA (`llama3.2` e `nomic-embed-text`) automaticamente.
4. Execute a aplicação no terminal do VS Code:
   ```bash
   ./mvnw spring-boot:run
   ```
5. Acesse a documentação gráfica do Swagger em: `http://localhost:8080/swagger-ui.html`

---

## 💻 Como Executar Localmente

```bash
# 1. Subir a infraestrutura (Postgres, Redis, Kafka, Ollama)
docker-compose up -d

# 2. Compilar e rodar a aplicação Spring Boot
./mvnw spring-boot:run
```

---

## 🔐 Endpoints Principais (API REST)

* **Autenticação**:
  * `POST /api/v1/auth/register` - Criar conta
  * `POST /api/v1/auth/login` - Obter Token JWT
* **Artigos**:
  * `POST /api/v1/articles` - Cadastrar novo artigo (Requer Header `Authorization: Bearer <TOKEN>`)
  * `GET /api/v1/articles` - Listar artigos
* **Inteligência Artificial & RAG**:
  * `POST /api/v1/ai/ask` - Realizar pergunta ao modelo local (Ollama) utilizando RAG.
