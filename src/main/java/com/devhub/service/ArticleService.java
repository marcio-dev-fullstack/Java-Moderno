package com.devhub.service;

import com.devhub.domain.Article;
import com.devhub.domain.dto.ArticleDTOs.ArticleRequestDTO;
import com.devhub.domain.dto.ArticleDTOs.ArticleResponseDTO;
import com.devhub.repository.ArticleRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final DocumentIngestionService documentIngestionService;

    public ArticleService(ArticleRepository articleRepository, DocumentIngestionService documentIngestionService) {
        this.articleRepository = articleRepository;
        this.documentIngestionService = documentIngestionService;
    }

    @CacheEvict(value = "articles", allEntries = true)
    public ArticleResponseDTO createArticle(ArticleRequestDTO dto) {
        Article article = new Article();
        article.setTitle(dto.title());
        article.setContent(dto.content());
        article.setAuthor(dto.author());
        article.setTags(dto.tags());
        article.setCreatedAt(LocalDateTime.now());

        Article saved = articleRepository.save(article);
        documentIngestionService.ingestDocument(saved.getId().toString(), saved.getContent());

        return toDTO(saved);
    }

    @Cacheable(value = "articles")
    public List<ArticleResponseDTO> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Cacheable(value = "article_details", key = "#id")
    public ArticleResponseDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artigo não encontrado com ID: " + id));
        return toDTO(article);
    }

    private ArticleResponseDTO toDTO(Article article) {
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
