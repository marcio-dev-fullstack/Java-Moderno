package com.devhub.service;

import com.devhub.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentIngestionService {

    private final VectorStore vectorStore;

    public void ingestArticle(Article article) {
        Document doc = new Document(
                article.getContent(),
                Map.of("articleId", article.getId(), "title", article.getTitle(), "author", article.getAuthor() != null ? article.getAuthor() : "Desconhecido")
        );

        TokenTextSplitter textSplitter = new TokenTextSplitter();
        List<Document> splitDocs = textSplitter.apply(List.of(doc));

        vectorStore.accept(splitDocs);
    }
}
