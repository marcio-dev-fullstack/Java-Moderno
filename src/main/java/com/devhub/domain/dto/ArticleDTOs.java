package com.devhub.domain.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleDTOs {

    public record ArticleRequestDTO(
        @NotBlank(message = "Título é obrigatório") String title,
        @NotBlank(message = "Conteúdo é obrigatório") String content,
        String author,
        List<String> tags
    ) {}

    public record ArticleResponseDTO(
        Long id,
        String title,
        String content,
        String author,
        List<String> tags,
        LocalDateTime createdAt
    ) {}

    public record ArticleCreatedEvent(
        Long id,
        String title,
        String author
    ) {}
}
