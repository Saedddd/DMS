package com.example.dms.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status {

    @JsonView(Views.Basic.class)
    @Id
    @GeneratedValue
    private UUID id;

    @JsonView(Views.Basic.class)
    @Column(nullable = false, unique = true)
    private String name;

    @JsonView(Views.Detailed.class)
    @Column(nullable = false)
    private boolean isFinal;

    @JsonView(Views.Internal.class)
    @Column(nullable = false)
    private Integer orderIndex;
}

