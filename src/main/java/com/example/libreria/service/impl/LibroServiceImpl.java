package com.example.libreria.service.impl;

import com.example.libreria.dto.LibroDTO;
import com.example.libreria.entity.Libro;
import com.example.libreria.error.RisorsaNonTrovataException;
import com.example.libreria.mapper.LibroMapper;
import com.example.libreria.repository.LibroRepository;
import com.example.libreria.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<LibroDTO> findAll() {
        return libroRepository.findAll().stream()
                .map(LibroMapper::toDTO)
                .toList();
    }

    @Override
    public LibroDTO findById(Long id) {
        return libroRepository.findById(id)
                .map(LibroMapper::toDTO)
                .orElseThrow(() -> new RisorsaNonTrovataException("Libro non trovato con id: " + id));
    }

    @Override
    public List<LibroDTO> findByTitolo(String titolo) {
        return libroRepository.findByTitoloContainingIgnoreCase(titolo).stream()
                .map(LibroMapper::toDTO)
                .toList();
    }

    @Override
    public List<LibroDTO> findByAutore(String autore) {
        return libroRepository.findByAutoreContainingIgnoreCase(autore).stream()
                .map(LibroMapper::toDTO)
                .toList();
    }

    @Override
    public LibroDTO findByIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn)
                .map(LibroMapper::toDTO)
                .orElseThrow(() -> new RisorsaNonTrovataException("Libro non trovato con ISBN: " + isbn));
    }

    @Override
    public LibroDTO save(LibroDTO libroDTO) {
        if (libroDTO.getId() == null) {
            Libro nuovo = LibroMapper.toEntity(libroDTO);
            return LibroMapper.toDTO(libroRepository.save(nuovo));
        }

        Libro libro = libroRepository.findById(libroDTO.getId())
                .orElseThrow(() -> new RisorsaNonTrovataException(
                        "Impossibile modificare: Libro con id " + libroDTO.getId() + " non trovato."));

        libro.setTitolo(libroDTO.getTitolo());
        libro.setAutore(libroDTO.getAutore());
        libro.setIsbn(libroDTO.getIsbn());
        libro.setCategoria(libroDTO.getCategoria());
        libro.setAnnoPubblicazione(libroDTO.getAnnoPubblicazione());
        libro.setPrezzo(libroDTO.getPrezzo());

        return LibroMapper.toDTO(libroRepository.save(libro));
    }

    @Override
    public void deleteById(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RisorsaNonTrovataException("Impossibile eliminare: Libro con id " + id + " non trovato.");
        }
        libroRepository.deleteById(id);
    }
}
