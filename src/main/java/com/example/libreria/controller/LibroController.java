package com.example.libreria.controller;

import com.example.libreria.dto.LibroDTO;
import com.example.libreria.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libri")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public ResponseEntity<List<LibroDTO>> getAll() {
        return ResponseEntity.ok(libroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.findById(id));
    }

    @GetMapping("/cerca/titolo")
    public ResponseEntity<List<LibroDTO>> getByTitolo(@RequestParam String titolo) {
        return ResponseEntity.ok(libroService.findByTitolo(titolo));
    }


    @GetMapping("/cerca/autore")
    public ResponseEntity<List<LibroDTO>> getByAutore(@RequestParam String autore) {
        return ResponseEntity.ok(libroService.findByAutore(autore));
    }

    @GetMapping("/cerca/isbn")
    public ResponseEntity<LibroDTO> getByIsbn(@RequestParam String isbn) {
        return ResponseEntity.ok(libroService.findByIsbn(isbn));
    }

    @PostMapping("/salva")
    public ResponseEntity<LibroDTO> save(@Valid @RequestBody LibroDTO libroDTO) {
        boolean creazione = libroDTO.getId() == null;
        LibroDTO salvato = libroService.save(libroDTO);
        return ResponseEntity.status(creazione ? HttpStatus.CREATED : HttpStatus.OK).body(salvato);
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        libroService.deleteById(id);
        return ResponseEntity.ok("Libro eliminato con successo");
    }
}
