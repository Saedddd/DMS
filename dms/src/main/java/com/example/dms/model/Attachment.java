package com.example.dms.model;

import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {

    @JsonView(Views.Basic.class)
    @Id
    @GeneratedValue
    private UUID id;

    @JsonView(Views.Basic.class)
    @Column(nullable = false)
    private String fileName;

    @JsonView(Views.Internal.class)
    @Column(nullable = false)
    private String filePath;

    @JsonView(Views.Detailed.class)
    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @JsonView(Views.Detailed.class)
    @ManyToOne
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    @JsonView(Views.Detailed.class)
    @JsonSerialize(using = InstantSerializer.class)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant uploadedAt;
}

