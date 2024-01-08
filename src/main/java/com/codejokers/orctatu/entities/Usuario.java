package com.codejokers.orctatu.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idGoogle;

    @OneToMany(mappedBy = "usuario")
    private List<Budget> budgets;

    public Usuario(String idGoogle) {
        this.idGoogle = idGoogle;
    }
}