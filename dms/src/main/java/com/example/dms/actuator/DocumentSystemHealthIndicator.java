package com.example.dms.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import com.example.dms.repository.DocumentRepository;

@Component
public class DocumentSystemHealthIndicator implements HealthIndicator {
    
    private final DocumentRepository documentRepository;
    
    public DocumentSystemHealthIndicator(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public Health health() {
        try {
            long documentCount = documentRepository.count();
            return Health.up()
                    .withDetail("documentCount", documentCount)
                    .withDetail("status", "Document system is operational")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", "Cannot connect to document system")
                    .withException(e)
                    .build();
        }
    }
}