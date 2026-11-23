package com.devhub.service;

import com.devhub.domain.dto.ArticleDTOs.*;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VectorSearchService {

    private final ChatModel chatModel;

    public VectorSearchService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public AiAskResponse askWithRag(String question) {
        String promptText = "Com base no conhecimento disponível, responda de forma clara e precisa: " + question;
        String response = chatModel.call(promptText);
        return new AiAskResponse(response, List.of("Base de Conhecimento Local / Ollama"));
    }
}
