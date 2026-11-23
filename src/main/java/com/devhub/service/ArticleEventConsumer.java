package com.devhub.service;

import com.devhub.domain.ArticleCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ArticleEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(ArticleEventConsumer.class);

    @KafkaListener(topics = "article-created-topic", groupId = "devhub-group")
    public void consume(ArticleCreatedEvent event) {
        log.info("Evento Kafka Recebido! Artigo '{}' (Categoria: {}) processado assincronamente.",
                event.title(), event.category());
    }
}
