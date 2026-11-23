package com.devhub.controller;

import com.devhub.domain.dto.ArticleDTOs.*;
import com.devhub.service.VectorSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final VectorSearchService vectorSearchService;

    public AiController(VectorSearchService vectorSearchService) {
        this.vectorSearchService = vectorSearchService;
    }

    @PostMapping("/ask")
    public ResponseEntity<AiAskResponse> ask(@RequestBody AiAskRequest request) {
        return ResponseEntity.ok(vectorSearchService.askWithRag(request.question()));
    }
}
