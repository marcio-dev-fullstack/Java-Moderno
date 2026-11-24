# Dev Knowledge AI Hub (Central de Conhecimento em IA para Desenvolvedores)
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

  <!-- RODAPÉ -->
<footer id="contato" class="bg-slate-900 text-white py-12 border-t border-slate-800">
    <div class="max-w-7xl mx-auto px-4 text-center">
        <h3 class="text-2xl font-bold mb-2">Quer dar vida ao seu projeto?</h3>
        <p class="text-slate-400 mb-2">Entre em contato conosco e solicite uma avaliação técnica ou orçamento sem compromisso.</p>
        
        <a href="https://wa.me/5562996466033" target="_blank" class="inline-flex items-center gap-2 bg-amber-500 hover:bg-amber-600 text-slate-900 font-bold px-6 py-3 rounded-lg transition mb-6 text-lg">
            <i class="fa-brands fa-whatsapp text-2xl"></i> Falar no WhatsApp: (62) 99646-6033
        </a>

        <div class="text-slate-300 text-sm mb-6 flex items-center justify-center gap-2">
            <i class="fa-solid fa-location-dot text-amber-500"></i>
            <span>Av. Portugal, 1148, Quadra L29, Lote 1E, Sala C2501, Setor Marista, Goiânia - GO, CEP 74150-030</span>
        </div>

        <div class="border-t border-slate-800 pt-6 mt-4 text-xs text-slate-500 flex flex-col sm:flex-row justify-between items-center gap-2">
            <p class="text-center sm:text-left">Zentix Engenharia & Tecnologia 
            <br> © 2022. Todos os direitos reservados</p>
            <p>Desenvolvido por: <a href="http://zentix.eng.br/" target="_blank" class="text-amber-500 hover:underline font-bold">ZENTIX</a></p>
        </div>
    </div>
</footer>
