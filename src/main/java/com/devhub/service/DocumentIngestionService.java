package com.devhub.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DocumentIngestionService {

    private final VectorStore vectorStore;

    public DocumentIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void ingestArticle(String articleId, String title, String content, String author) {
        Document rawDoc = new Document(content, Map.of(
                "articleId", articleId,
                "title", title,
                "author", author
        ));

        // Token-based Chunking (Split into manageable chunks for embeddings)
        TokenTextSplitter splitter = new TokenTextSplitter(800, 350, 5, 10000, true);
        List<Document> chunks = splitter.apply(List.of(rawDoc));

        // Index in Vector DB (pgvector)
        vectorStore.accept(chunks);
    }
}
