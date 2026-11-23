package com.devhub.domain;

import java.time.LocalDateTime;

/**
 * Record Java 21 para a resposta formatada do artigo.
 */
public record ArticleResponseDTO(
    Long id,
    String title,
    String content,
    String category,
    LocalDateTime createdAt
) {
    // Método utilitário de conveniência utilizando Pattern Matching e Records
    public static ArticleResponseDTO fromEntity(Article article) {
        return new ArticleResponseDTO(
            article.getId(),
            article.getTitle(),
            article.getContent(),
            article.getCategory(),
            article.getCreatedAt()
        );
    }
}
