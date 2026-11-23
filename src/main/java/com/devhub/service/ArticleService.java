package com.devhub.service;

import com.devhub.domain.Article;
import com.devhub.domain.ArticleCreatedEvent;
import com.devhub.domain.ArticleRequestDTO;
import com.devhub.domain.ArticleResponseDTO;
import com.devhub.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleEventProducer eventProducer;

    public ArticleService(ArticleRepository articleRepository, ArticleEventProducer eventProducer) {
        this.articleRepository = articleRepository;
        this.eventProducer = eventProducer;
    }

    @Transactional
    public ArticleResponseDTO createArticle(ArticleRequestDTO dto) {
        Article article = new Article(dto.title(), dto.content(), dto.category());
        Article saved = articleRepository.save(article);

        // Dispara evento Kafka assíncrono (Etapa 3 - Mensageria)
        eventProducer.sendArticleCreatedEvent(
            new ArticleCreatedEvent(saved.getId(), saved.getTitle(), saved.getCategory())
        );

        return ArticleResponseDTO.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDTO> listAllArticles() {
        // Uso declarativo da API de Streams (Etapa 1 - Core Java)
        return articleRepository.findAll().stream()
                .map(ArticleResponseDTO::fromEntity)
                .toList();
    }
}
