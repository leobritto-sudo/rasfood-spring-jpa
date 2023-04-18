package com.rasmoo.api.rasfood.controller;

import com.rasmoo.api.rasfood.entity.Cliente;
import com.rasmoo.api.rasfood.entity.ClienteId;
import com.rasmoo.api.rasfood.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/clientes")
@RestController
public class ClienteController {
    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping(value = "/consultar")
    private ResponseEntity<List<Cliente>> consultarClientes() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.findAll());
    }

    @GetMapping(value = "/consultar/{email}/{cpf}")
    private ResponseEntity<Cliente> consultarPorEmailCpf(
            @PathVariable(value = "email") String email,
            @PathVariable(value = "cpf") String cpf) {
        
        ClienteId clienteId = new ClienteId(email, cpf);
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);

        if(cliente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
    }
}
