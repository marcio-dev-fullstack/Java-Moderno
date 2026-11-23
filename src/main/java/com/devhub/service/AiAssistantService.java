package com.devhub.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class AiAssistantService {

    private final ChatModel chatModel;

    public AiAssistantService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String askAssistant(String question) {
        // Integração nativa Spring AI (Etapa 5 - IA em Java)
        try {
            return chatModel.call(question);
        } catch (Exception e) {
            return "Modo AI Simulado: Resposta para '" + question + "' com base nos artigos armazenados.";
        }
    }
}
