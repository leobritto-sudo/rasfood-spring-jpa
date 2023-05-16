package com.rasmoo.api.rasfood.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.rasfood.entity.Cliente;
import com.rasmoo.api.rasfood.entity.ClienteId;
import com.rasmoo.api.rasfood.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/clientes")
@RestController
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/consultar")
    public ResponseEntity<Page<Cliente>> consultarClientes(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.findAll(pageable));
    }

    @GetMapping(value = "/consultar", params = {"email", "cpf"})
    public ResponseEntity<Cliente> consultarPorEmailCpf(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpf) {

        return clienteRepository.findById(new ClienteId(email, cpf))
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PatchMapping(value = "/atualizar", params = {"id"})
    public ResponseEntity<Cliente> atualizarCliente(
            @RequestParam String id, @RequestBody Cliente cliente) throws JsonMappingException {

        Optional<Cliente> clienteFound = clienteRepository.findByEmailOrCpf(id);
        if (clienteFound.isPresent()) {
            objectMapper.updateValue(clienteFound.get(), cliente);
            return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.save(clienteFound.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
