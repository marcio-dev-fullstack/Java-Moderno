package com.devhub.controller;

import com.devhub.domain.ArticleRequestDTO;
import com.devhub.domain.ArticleResponseDTO;
import com.devhub.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDTO> create(@RequestBody @Valid ArticleRequestDTO dto) {
        ArticleResponseDTO response = articleService.createArticle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDTO>> listAll() {
        return ResponseEntity.ok(articleService.listAllArticles());
    }
}
