package com.example.dms.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @JsonView(Views.Basic.class)
    @Id
    @GeneratedValue
    private UUID id;

    @JsonView(Views.Basic.class)
    @Column(nullable = false, unique = true)
    private String name;
}
