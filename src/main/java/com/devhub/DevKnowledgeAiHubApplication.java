package com.devhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevKnowledgeAiHubApplication {

    public static void main(String[] args) {
        // Habilita suporte nativo às Virtual Threads do Java 21 no Spring Boot
        System.setProperty("spring.threads.virtual.enabled", "true");
        SpringApplication.run(DevKnowledgeAiHubApplication.class, args);
    }
}
