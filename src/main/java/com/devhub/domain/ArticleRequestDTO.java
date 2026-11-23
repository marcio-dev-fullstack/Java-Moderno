package com.devhub.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Record Java 21 representando um DTO imutável de requisição.
 */
public record ArticleRequestDTO(
    @NotBlank(message = "O título não pode ser vazio")
    @Size(min = 3, max = 150)
    String title,

    @NotBlank(message = "O conteúdo é obrigatório")
    String content,

    @NotBlank(message = "A categoria é obrigatória")
    String category
) {}
