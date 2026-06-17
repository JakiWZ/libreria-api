package com.example.libreria.controller;

import com.example.libreria.dto.LibroDTO;
import com.example.libreria.error.RisorsaNonTrovataException;
import com.example.libreria.service.LibroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibroController.class)
class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private LibroService libroService;

    @Test
    void getAll_restituisceLaListaDeiLibri() throws Exception {
        LibroDTO libro = LibroDTO.builder()
                .id(1L).titolo("Il nome della rosa").autore("Umberto Eco").isbn("123").build();
        given(libroService.findAll()).willReturn(List.of(libro));

        mockMvc.perform(get("/api/libri"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titolo").value("Il nome della rosa"));
    }

    @Test
    void getById_quandoNonEsiste_restituisce404() throws Exception {
        given(libroService.findById(99L))
                .willThrow(new RisorsaNonTrovataException("Libro non trovato con id: 99"));

        mockMvc.perform(get("/api/libri/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void save_conDatiNonValidi_restituisce400_eNonChiamaIlService() throws Exception {
        LibroDTO libroNonValido = LibroDTO.builder().titolo("").autore("").isbn("").build();

        mockMvc.perform(post("/api/libri/salva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libroNonValido)))
                .andExpect(status().isBadRequest());

        verify(libroService, never()).save(libroNonValido);
    }
}
