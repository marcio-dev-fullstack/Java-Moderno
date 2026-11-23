package com.devhub;

import com.devhub.domain.ArticleRequestDTO;
import com.devhub.domain.ArticleResponseDTO;
import com.devhub.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class DevKnowledgeAiHubApplicationTests {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("pgvector/pgvector:pg16")
            .withDatabaseName("devhub_db_test")
            .withUsername("test_user")
            .withPassword("test_pass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ArticleService articleService;

    @Test
    @DisplayName("Deve criar e persisitir um artigo com sucesso utilizando PostgreSQL em container real")
    void shouldCreateArticleSuccessfully() {
        ArticleRequestDTO dto = new ArticleRequestDTO(
            "Java 21 Virtual Threads",
            "Entendendo o Project Loom no Spring Boot 3",
            "Java"
        );

        ArticleResponseDTO response = articleService.createArticle(dto);

        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.title()).isEqualTo("Java 21 Virtual Threads");
    }
}
