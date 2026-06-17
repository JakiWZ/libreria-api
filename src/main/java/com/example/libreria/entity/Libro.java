package com.example.libreria.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "libri")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titolo;
    private String autore;

    @Column(unique = true)
    private String isbn;

    private String categoria;
    private Integer annoPubblicazione;
    private Double prezzo;
}
