package com.devhub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VectorSearchService {

    private final VectorStore vectorStore;
    private final ChatClient.Builder chatClientBuilder;

    public Map<String, Object> askQuestionWithRag(String question) {
        List<Document> similarDocs = vectorStore.similaritySearch(
                SearchRequest.query(question).withTopK(3)
        );

        String context = similarDocs.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n\n"));

        List<String> sourceTitles = similarDocs.stream()
                .map(doc -> (String) doc.getMetadata().getOrDefault("title", "Documento sem título"))
                .distinct()
                .toList();

        String prompt = String.format("""
                Você é um assistente técnico especialista. Responda à pergunta do usuário baseando-se estritamente no contexto fornecido abaixo.
                
                CONTEXTO:
                %s
                
                PERGUNTA:
                %s
                """, context, question);

        String answer = chatClientBuilder.build()
                .prompt()
                .user(prompt)
                .call()
                .content();

        return Map.of(
                "answer", answer,
                "sourceArticles", sourceTitles
        );
    }
}
