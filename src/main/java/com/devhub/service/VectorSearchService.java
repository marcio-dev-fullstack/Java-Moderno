package com.devhub.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectorSearchService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public VectorSearchService(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
    }

    public List<Document> searchSimilar(String query, int topK, double threshold) {
        SearchRequest request = SearchRequest.query(query)
                .withTopK(topK)
                .withSimilarityThreshold(threshold);

        return vectorStore.similaritySearch(request);
    }

    public String askWithRagContext(String userQuestion) {
        List<Document> similarDocs = searchSimilar(userQuestion, 5, 0.7);
        
        StringBuilder context = new StringBuilder();
        for (Document doc : similarDocs) {
            context.append(doc.getContent()).append("\n---");
        }

        String promptTemplate = """
                Você é o assistente técnico especialista do Dev Knowledge Hub.
                Responda à dúvida do usuário baseando-se estritamente no contexto técnico fornecido abaixo.
                Se a resposta não estiver no contexto, responda honestamente que não possui essa informação no momento.

                CONTEXTO RECUPERADO:
                {context}

                PERGUNTA DO USUÁRIO:
                {question}
                """;

        return chatClient.prompt()
                .user(userSpec -> userSpec.text(promptTemplate)
                        .param("context", context.toString())
                        .param("question", userQuestion))
                .call()
                .content();
    }
}
