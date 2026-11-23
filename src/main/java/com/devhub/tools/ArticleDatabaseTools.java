package com.devhub.tools;

import com.devhub.domain.ArticleResponseDTO;
import com.devhub.service.ArticleService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleDatabaseTools {

    private final ArticleService articleService;

    public ArticleDatabaseTools(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Tool(description = "Busca artigos recentes cadastrados no sistema por palavra-chave ou autor")
    public List<ArticleResponseDTO> searchDatabaseArticles(String keyword) {
        return articleService.findAllArticles().stream()
                .filter(a -> a.title().toLowerCase().contains(keyword.toLowerCase()) || 
                             a.author().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
}
