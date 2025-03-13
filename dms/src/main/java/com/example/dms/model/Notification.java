package com.example.dms.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @JsonView(Views.Basic.class)
    @Id
    @GeneratedValue
    private UUID id;

    @JsonView(Views.Basic.class)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @JsonView(Views.Basic.class)
    @Column(nullable = false)
    private boolean isRead;

    @JsonView(Views.Detailed.class)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonView(Views.Detailed.class)
    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @JsonView(Views.Detailed.class)
    @JsonSerialize(using = InstantSerializer.class)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
