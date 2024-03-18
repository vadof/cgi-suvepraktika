package com.cgi.api.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "genre")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genre {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

}
