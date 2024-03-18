package com.cgi.api.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "language")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Language {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value", unique = true, nullable = false)
    private String value;
}
