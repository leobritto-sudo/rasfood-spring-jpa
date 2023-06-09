package com.rasmoo.api.rasfood.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.dto.CardapioDTO;
import com.rasmoo.api.rasfood.entity.Cardapio;
import com.rasmoo.api.rasfood.repository.CardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/cardapio")
@RestController
public class CardapioController {

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/consultar")
    public ResponseEntity<Page<Cardapio>> consultarCardapios(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
            ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nome"));
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAll(pageable));
    }

    @GetMapping(value = "/consultar", params = "categoriaId")
    public ResponseEntity<Page<Cardapio>> consultarCardapiosPorCategoria(
            @RequestParam(required = false) Integer categoriaId,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAllByCategoria(categoriaId, pageable));
    }

    @GetMapping(value = "/consultar", params = "nome")
    public ResponseEntity<Page<CardapioDTO>> consultarCardapiosPorNome(
            @RequestParam(required = false) String nome,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.findAllByName(nome, pageable));
    }

    @GetMapping(value = "/consultar", params = "id")
    public ResponseEntity<Cardapio> consultarPorId(
            @RequestParam(required = false) Integer id) {

        return cardapioRepository.findById(id)
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping(value = "/deletar", params = "id")
    public ResponseEntity<Void> excluirPorId(
            @RequestParam Integer id) {

        Optional<Cardapio> cardapioFound = cardapioRepository.findById(id);
        if(cardapioFound.isPresent()) {
            cardapioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping(value = "/inserir")
    public ResponseEntity<Cardapio> atualizarCardapio(@RequestBody Cardapio cardapio) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cardapioRepository.save(cardapio));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/atualizar", params = {"id"})
    public ResponseEntity<Cardapio> atualizarCardapio(
            @RequestParam Integer id, @RequestBody Cardapio cardapio) throws JsonMappingException {

        Optional<Cardapio> cardapioFound = cardapioRepository.findById(id);
        if (cardapioFound.isPresent()) {
            objectMapper.updateValue(cardapioFound.get(), cardapio);
            return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.save(cardapioFound.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
