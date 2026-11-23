package com.devhub.domain;

import java.io.Serializable;

public record ArticleCreatedEvent(
    Long articleId,
    String title,
    String category
) implements Serializable {}
