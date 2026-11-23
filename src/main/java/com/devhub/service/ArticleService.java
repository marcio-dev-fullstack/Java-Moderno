package com.devhub.service;

import com.devhub.domain.Article;
import com.devhub.domain.dto.ArticleDTOs.*;
import com.devhub.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final DocumentIngestionService documentIngestionService;

    public ArticleResponseDTO createArticle(ArticleRequestDTO dto) {
        Article article = Article.builder()
                .title(dto.title())
                .content(dto.content())
                .author(dto.author())
                .tags(dto.tags())
                .build();

        Article saved = articleRepository.save(article);

        // Ingestão no Vector DB (pgvector)
        documentIngestionService.ingestArticle(saved);

        // Publica evento Kafka
        ArticleCreatedEvent event = new ArticleCreatedEvent(saved.getId(), saved.getTitle(), saved.getAuthor());
        kafkaTemplate.send("article-created-topic", saved.getId().toString(), event);

        return mapToDTO(saved);
    }

    public List<ArticleResponseDTO> getAllArticles() {
        return articleRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public ArticleResponseDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artigo não encontrado com ID: " + id));
        return mapToDTO(article);
    }

    private ArticleResponseDTO mapToDTO(Article article) {
        return new ArticleResponseDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor(),
                article.getTags(),
                article.getCreatedAt()
        );
    }
}
