package com.devhub.controller;

import com.devhub.domain.dto.ArticleDTOs.*;
import com.devhub.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
@Tag(name = "Artigos", description = "Endpoints de gerenciamento e publicação de artigos técnicos")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    @Operation(summary = "Criar novo artigo", description = "Cria um artigo, dispara o evento no Kafka e gera embeddings no pgvector.")
    public ResponseEntity<ArticleResponseDTO> createArticle(@Valid @RequestBody ArticleRequestDTO request) {
        ArticleResponseDTO response = articleService.createArticle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os artigos")
    public ResponseEntity<List<ArticleResponseDTO>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar artigo por ID")
    public ResponseEntity<ArticleResponseDTO> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }
}
