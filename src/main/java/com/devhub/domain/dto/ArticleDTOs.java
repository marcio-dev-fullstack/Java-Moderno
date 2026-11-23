package com.devhub.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDTOs {
    public record ArticleRequestDTO(String title, String content, String author, List<String> tags) {}
    public record ArticleResponseDTO(Long id, String title, String content, String author, List<String> tags, LocalDateTime createdAt) {}
    public record AiAskRequest(String question) {}
    public record AiAskResponse(String answer, List<String> sourceArticles) {}
}
