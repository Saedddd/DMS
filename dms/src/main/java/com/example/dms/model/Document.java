package com.example.dms.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.example.dms.serialization.InstantSerializer;
import com.example.dms.model.Views;

@Entity
@Table(name = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {

    @JsonView(Views.Basic.class)
    @Id
    @GeneratedValue
    private UUID id;

    @JsonView(Views.Basic.class)
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @JsonView(Views.Basic.class)
    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonView(Views.Detailed.class)
    @NotNull(message = "Status is required")
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @JsonView(Views.Detailed.class)
    @JsonSerialize(using = InstantSerializer.class)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @JsonView(Views.Detailed.class)
    @JsonSerialize(using = InstantSerializer.class)
    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    @JsonView(Views.Detailed.class)
    @NotNull(message = "Owner is required")
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}

