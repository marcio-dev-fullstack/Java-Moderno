package com.devhub.config;

import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreProperties;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class VectorStoreConfig {

    @Bean
    public VectorStore vectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel, PgVectorStoreProperties properties) {
        return new PgVectorStore(jdbcTemplate, embeddingModel, properties.getDimensions(), properties.getDistanceType(),
                properties.isRemoveExistingVectorStoreTable(), properties.getIndexType());
    }
}
