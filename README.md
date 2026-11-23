# Dev Knowledge AI Hub (v3) — Guia de Arquitetura e Implementação com Segurança JWT

## 1. Visão Geral e Alinhamento com o Guia do Engenheiro Java Moderno

O **Dev Knowledge AI Hub** é uma plataforma backend de nível corporativo e de produção desenvolvida para exemplificar a construção de microsserviços modernos no ecossistema Java. A aplicação integra gestão de acervo técnico, busca semântica orientada por Inteligência Artificial via **RAG (Retrieval-Augmented Generation)**, mensageria assíncrona baseada em eventos e uma arquitetura de segurança stateless reforçada por tokens JWT.

A arquitetura do sistema reflete diretamente as **seis etapas fundamentais** estabelecidas no **Guia do Engenheiro Java Moderno**, articulando desde a linguagem base até a operação em nuvem:

| Etapa do Guia | Tecnologia Integrada | Função e Aplicação Prática no Projeto |
| :--- | :--- | :--- |
| **1. Core Java (Java Essencial)** | Java 21 LTS, Virtual Threads, Records, Pattern Matching | Addoção do Java 21 LTS com Virtual Threads (Project Loom) para concorrência de alto desempenho em tarefas de I/O, DTOs imutáveis através de Records, Pattern Matching e processamento declarativo com Streams API. |
| **2. Backend com Spring** | Spring Boot 3.3.2, Spring Data JPA, Spring Security 6 | Construção de APIs RESTful padronizadas, persistência ORM com Hibernate/PostgreSQL, e camada de segurança stateless fundamentada em autenticação e autorização por tokens JWT. |
| **3. Dados & Mensageria** | PostgreSQL 16 (pgvector), Redis 7, Apache Kafka | Persistência relacional e vetorial no PostgreSQL com extensão `pgvector`, cache de alta velocidade no Redis e desacoplamento de eventos de domínio com Apache Kafka. |
| **4. Cloud & DevOps** | Docker Multi-Stage, Kubernetes, Terraform, GitHub Actions | Conteinização com imagem minimalista Alpine, infraestrutura como código (IaC) com Terraform para AWS RDS/ECR, manifestos Kubernetes e esteiras de CI/CD para AWS ECS e Azure. |
| **5. IA em Java** | Spring AI, PgVectorStore, Tool Calling (MCP / `@Tool`) | Integração com modelos LLM (OpenAI/Ollama local), chunking de texto com `TokenTextSplitter`, busca semântica vetorial e invocação dinâmica de métodos Java decorados com `@Tool`. |
| **6. Excelência em Produção** | Testcontainers, JUnit 5, Spring Boot Actuator, Micrometer | Suíte de testes de integração com containers reais de banco de dados e mensageria, além de instrumentação de métricas para Prometheus e endpoints de observabilidade. |

---

## 2. Estrutura de Arquivos e Organização do Repositório GitHub

A organização do código fonte no repositório segue a arquitetura em camadas orientada a domínio, assegurando alta coesão, baixo acoplamento e clara segregação de responsabilidades:

```text
dev-knowledge-ai-hub/
├── .github/
│   └── workflows/
│       ├── ci.yml                 # Integração contínua: build Maven, testes e qualidade
│       ├── deploy-aws-ecs.yml     # Deploy automatizado no Amazon ECS via ECR
│       └── deploy-azure.yml       # Deploy no Azure Container Apps
├── k8s/                           # Manifestos de orquestração Kubernetes
│   ├── deployment.yaml            # Deployment dos pods do Spring Boot com Health Probes
│   └── service.yaml               # Service do Kubernetes para balanceamento de carga
├── terraform/                     # Infraestrutura como Código (IaC)
│   └── main.tf                    # Provisionamento de cluster AWS RDS (PostgreSQL) e ECR
├── .devcontainer/                 # Configuração para desenvolvimento no GitHub Codespaces
│   ├── devcontainer.json          # Ambiente pré-configurado com Java 21 e VS Code extensions
│   └── post-create.sh             # Script de inicialização automática dos modelos Ollama
├── docker-compose.yml             # Serviços locais: Postgres (pgvector), Redis, Kafka, Ollama
├── Dockerfile                     # Multi-stage build para geração da imagem de produção
├── pom.xml                        # Arquivo POM de dependências Maven do Spring Boot 3.3
├── README.md                      # Guia completo de documentação do projeto
└── src/
    ├── main/
    │   ├── java/com/devhub/
    │   │   ├── DevKnowledgeAiHubApplication.java
    │   │   ├── config/            # SecurityConfig, JwtTokenProvider, VectorStoreConfig, OpenApiConfig
    │   │   ├── domain/            # Entities (User, Article) e Records DTOs imutáveis
    │   │   ├── repository/        # UserRepository e ArticleRepository (Spring Data JPA)
    │   │   ├── service/           # AuthService, ArticleService, VectorSearchService, DocumentIngestionService
    │   │   └── controller/        # AuthController, ArticleController e AiController
    │   └── resources/
    │       └── application.yml    # Configurações do Actuator, Security, Kafka e Spring AI
    └── test/
        └── java/com/devhub/       # Testes de integração automatizados com Testcontainers
```

---

## 3. Camada de Segurança e Autenticação JWT (Versão v3)

A versão v3 adiciona uma camada de segurança robusta baseada em **Spring Security 6** e na biblioteca **jjwt 0.12.6**. A autenticação é 100% stateless, dispensando sessões HTTP em memória e garantindo escalabilidade horizontal.

### Arquitetura da Segurança Stateless

1. **Assinatura Criptográfica**: Ao realizar o login no endpoint `/api/v1/auth/login`, o sistema valida as credenciais contra a entidade `User` com senhas criptografadas via **BCryptPasswordEncoder**. Em caso de sucesso, gera um token JWT assinado com algoritmo **HMAC-SHA256**.
2. **Filtro de Interceptação (`JwtAuthenticationFilter`)**: Em cada requisição HTTP nos endpoints protegidos, o filtro extrai o token enviado no cabeçalho `Authorization: Bearer <TOKEN>`, valida a assinatura e a expiração do token.
3. **Injeção de Autoridade no Contexto**: Após a validação, as permissões do usuário (`ROLE_USER` ou `ROLE_ADMIN`) são atribuídas ao objeto `UsernamePasswordAuthenticationToken`, que é armazenado no `SecurityContextHolder` durante o ciclo de vida da requisição.

### Contratos dos Endpoints de Autenticação

#### Registro de Usuário (`POST /api/v1/auth/register`)
Endpoint público para criação de contas de acesso.

* **Payload de Requisição**:
  ```json
  {
    "username": "engenheiro_java",
    "email": "dev@knowledge.com",
    "password": "SenhaSegura123!",
    "role": "ROLE_ADMIN"
  }
  ```
* **Payload de Resposta (HTTP 201 Created)**:
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZXZAa25vd2xlZGdlLmNvbSIsImlhdCI6MTcyNjk...",
    "tokenType": "Bearer",
    "username": "engenheiro_java",
    "email": "dev@knowledge.com",
    "role": "ROLE_ADMIN"
  }
  ```

#### Autenticação / Login (`POST /api/v1/auth/login`)
Endpoint público para emissão de novos tokens JWT.

* **Payload de Requisição**:
  ```json
  {
    "email": "dev@knowledge.com",
    "password": "SenhaSegura123!"
  }
  ```

---

## 4. Persistência, Mensageria Assíncrona e Pipeline RAG

A arquitetura de dados integra bancos de dados relacionais e vetoriais com brokers de mensageria assíncrona e chamadas a modelos de linguagem.

### Processamento Assíncrono com Apache Kafka

Quando um novo artigo é cadastrado com sucesso, o `ArticleService` dispara um evento `ArticleCreatedEvent` através do `ArticleEventProducer`. 

O evento é postado no tópico Kafka `devhub-articles-topic`. O componente `ArticleEventConsumer` consome a mensagem em segundo plano utilizando Virtual Threads, executando atualizações de estatísticas e auditoria sem bloquear a resposta da API ao usuário final.

### Pipeline RAG com Spring AI e pgvector

O sistema implementa o padrão RAG (Retrieval-Augmented Generation) para responder a dúvidas técnicas com estrita fundamentação nos artigos armazenados:

1. **Ingestão e Divisão de Texto**: O `DocumentIngestionService` recebe o artigo cadastrado e o fragmenta em chunks vetoriais utilizando o `TokenTextSplitter`.
2. **Geração de Embeddings e Armazenamento**: O componente envia os chunks para o modelo de embedding (como `nomic-embed-text` do Ollama ou OpenAI Embeddings) e grava os vetores resultantes no banco PostgreSQL 16 configurado com `pgvector`.
3. **Busca Semântica e Sintetização**: Ao receber uma pergunta no endpoint `POST /api/v1/ai/ask`, o `VectorSearchService` consulta o `PgVectorStore`, recupera os trechos com maior similaridade de cosseno e constrói o contexto que instrui o modelo LLM a gerar uma resposta precisa e fundamentada.
4. **Tool Calling (MCP / `@Tool`)**: A classe `ArticleDatabaseTools` disponibiliza métodos anotados com `@Tool` (Model Context Protocol), permitindo que a própria LLM decida invocar consultas ao banco de dados Java dinamicamente quando precisar de dados em tempo real.

---

## 5. Infraestrutura, Containers e Deploy em Nuvem

A solução foi projetada para portabilidade total entre ambientes de desenvolvimento e nuvens públicas como AWS e Azure.

### Dockerfile Multi-Stage Otimizado

A compilação do código fonte e a geração da imagem de container ocorrem em etapas separadas para garantir imagens leves e seguras:

```dockerfile
# Etapa 1: Build da aplicação com Maven e Java 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagem minimalista de execução (JRE 21 Alpine)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
```

### Arquitetura do `docker-compose.yml`

O arquivo de composição local sobe os seguintes serviços de forma totalmente integrada:
* **PostgreSQL 16**: Com a extensão `pgvector` ativada.
* **Redis 7**: Para armazenamento de cache e gerenciamento de sessões temporárias.
* **Apache Kafka & Zookeeper**: Para mensageria orientada a eventos.
* **Ollama**: Motor de IA local executando os modelos `llama3.2` e `nomic-embed-text`.

---

## 6. Guia Prático de Execução, Testes REST e Observabilidade

### Passo a Passo para Execução Local

```bash
# 1. Subir a infraestrutura em containers
docker-compose up -d

# 2. Executar a aplicação Spring Boot 3.3
./mvnw spring-boot:run
```

### Execução de Testes REST Protegidos via `curl`

#### 1. Criar um Artigo Técnico (Exige Token JWT de ADMIN)
```bash
curl -X POST http://localhost:8080/api/v1/articles \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN_JWT_OBTIDO_NO_LOGIN>" \
  -d '{
    "title": "Concorrência Avançada com Virtual Threads no Java 21",
    "content": "Virtual Threads simplificam a programação concorrente permitindo que aplicações criem milhões de threads leves operadas pela JVM.",
    "author": "engenheiro_java",
    "tags": ["Java 21", "Concurrency", "JVM"]
  }'
```

#### 2. Consultar o Assistente RAG com Pergunta Vetorial
```bash
curl -X POST http://localhost:8080/api/v1/ai/ask \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN_JWT_OBTIDO_NO_LOGIN>" \
  -d '{
    "question": "Como as Virtual Threads facilitam a concorrência no Java 21?"
  }'
```

### Observabilidade e Métricas de Produção

A aplicação expõe métricas de diagnóstico através do **Spring Boot Actuator** e do **Micrometer**, configurados no `application.yml`:
* **Healthcheck**: `GET http://localhost:8080/actuator/health`
* **Métricas do Prometheus**: `GET http://localhost:8080/actuator/prometheus` (inclui contadores de requisições HTTP, uso de memória JVM e estatísticas de Virtual Threads).
* **Interface Swagger UI**: Documentação interativa OpenAPI 3.0 acessível diretamente pelo navegador em `http://localhost:8080/swagger-ui.html`. Ao acessar a interface, utilize o botão **Authorize** e cole o token JWT no formato `Bearer <TOKEN>` para executar requisições interativas.
