package com.devhub.controller;

import com.devhub.service.AiAssistantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiAssistantService aiAssistantService;

    public AiController(AiAssistantService aiAssistantService) {
        this.aiAssistantService = aiAssistantService;
    }

    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> ask(@RequestBody Map<String, String> request) {
        String userQuestion = request.getOrDefault("question", "Como usar Java 21 com IA?");
        String answer = aiAssistantService.askAssistant(userQuestion);
        return ResponseEntity.ok(Map.of("question", userQuestion, "answer", answer));
    }
}
