package com.example.libreria.service;

import com.example.libreria.dto.LibroDTO;

import java.util.List;

public interface LibroService {

    List<LibroDTO> findAll();

    LibroDTO findById(Long id);

    List<LibroDTO> findByTitolo(String titolo);

    List<LibroDTO> findByAutore(String autore);

    LibroDTO findByIsbn(String isbn);

    LibroDTO save(LibroDTO libroDTO);

    void deleteById(Long id);
}
