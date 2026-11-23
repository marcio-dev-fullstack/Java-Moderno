package com.devhub.controller;

import com.devhub.service.VectorSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
@Tag(name = "Inteligência Artificial (RAG)", description = "Endpoints de consulta semântica e respostas via LLM baseadas no Vector DB")
public class AiController {

    private final VectorSearchService vectorSearchService;

    public record QuestionRequest(String question) {}

    @PostMapping("/ask")
    @Operation(summary = "Perguntar ao assistente IA", description = "Recupera documentos via RAG no pgvector e gera a resposta fundamentada.")
    public ResponseEntity<Map<String, Object>> askAi(@RequestBody QuestionRequest request) {
        return ResponseEntity.ok(vectorSearchService.askQuestionWithRag(request.question()));
    }
}
