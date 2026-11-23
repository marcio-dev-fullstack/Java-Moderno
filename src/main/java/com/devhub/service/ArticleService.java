package com.devhub.service;

import com.devhub.domain.Article;
import com.devhub.domain.dto.ArticleDTOs.*;
import com.devhub.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public ArticleResponseDTO create(ArticleRequestDTO dto) {
        Article article = new Article(dto.title(), dto.content(), dto.author(), dto.tags());
        Article saved = repository.save(article);
        return new ArticleResponseDTO(saved.getId(), saved.getTitle(), saved.getContent(), saved.getAuthor(), saved.getTags(), saved.getCreatedAt());
    }

    public List<ArticleResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(a -> new ArticleResponseDTO(a.getId(), a.getTitle(), a.getContent(), a.getAuthor(), a.getTags(), a.getCreatedAt()))
                .toList();
    }
}
