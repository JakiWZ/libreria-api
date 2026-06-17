package com.example.libreria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroDTO {

    private Long id;

    @NotBlank(message = "Il titolo è obbligatorio")
    private String titolo;

    @NotBlank(message = "L'autore è obbligatorio")
    private String autore;

    @NotBlank(message = "L'ISBN è obbligatorio")
    private String isbn;

    private String categoria;

    private Integer annoPubblicazione;

    @PositiveOrZero(message = "Il prezzo non può essere negativo")
    private Double prezzo;

}
