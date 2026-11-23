package com.devhub.service;

import com.devhub.domain.ArticleCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ArticleEventProducer {

    private static final Logger log = LoggerFactory.getLogger(ArticleEventProducer.class);
    private static final String TOPIC = "article-created-topic";

    private final KafkaTemplate<String, ArticleCreatedEvent> kafkaTemplate;

    public ArticleEventProducer(KafkaTemplate<String, ArticleCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendArticleCreatedEvent(ArticleCreatedEvent event) {
        log.info("Disparando evento Kafka para o artigo ID: {}", event.articleId());
        this.kafkaTemplate.send(TOPIC, String.valueOf(event.articleId()), event);
    }
}
