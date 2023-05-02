package com.rasmoo.api.rasfood.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.entity.Endereco;
import com.rasmoo.api.rasfood.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/endereco")
@RestController
public class EnderecoController {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/consultar")
    public ResponseEntity<List<Endereco>> consultarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findAll());
    }

    @GetMapping(value = "/consultar", params = "cep")
    public ResponseEntity<List<Endereco>> consultarTodosPorCep(@RequestParam(value = "cep", required = false) String cep){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findByCep(cep));
    }

    @GetMapping(value = "/consultar", params = "id")
    public ResponseEntity<Endereco> consultarPorId(@RequestParam(value = "id", required = false) final Integer id){
        return enderecoRepository.findById(id)
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PatchMapping(value = "/atualizar", params = "id")
    public ResponseEntity<Endereco> atualizar(@RequestParam("id") final Integer id, @RequestBody final Endereco endereco) throws JsonMappingException {
        Optional<Endereco> enderecoEncontrado = this.enderecoRepository.findById(id);
        if (enderecoEncontrado.isPresent()){
            objectMapper.updateValue(enderecoEncontrado.get(),endereco);
            return ResponseEntity.status(HttpStatus.OK).body(this.enderecoRepository.save(enderecoEncontrado.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
