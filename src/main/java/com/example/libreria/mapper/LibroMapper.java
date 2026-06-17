package com.example.libreria.mapper;

import com.example.libreria.dto.LibroDTO;
import com.example.libreria.entity.Libro;

public class LibroMapper {

    public static LibroDTO toDTO(Libro entity) {
        if (entity == null) return null;
        return LibroDTO.builder()
                .id(entity.getId())
                .titolo(entity.getTitolo())
                .autore(entity.getAutore())
                .isbn(entity.getIsbn())
                .categoria(entity.getCategoria())
                .annoPubblicazione(entity.getAnnoPubblicazione())
                .prezzo(entity.getPrezzo())
                .build();
    }

    public static Libro toEntity(LibroDTO dto) {
        if (dto == null) return null;
        return Libro.builder()
                .titolo(dto.getTitolo())
                .autore(dto.getAutore())
                .isbn(dto.getIsbn())
                .categoria(dto.getCategoria())
                .annoPubblicazione(dto.getAnnoPubblicazione())
                .prezzo(dto.getPrezzo())
                .build();
    }
}
