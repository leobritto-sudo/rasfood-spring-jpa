package com.rasmoo.api.rasfood.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/cardapio")
@RestController
public class CardapioController {

    @GetMapping(value = "/hello")
    private String helloWorld() {
        return "Hello World!";
    }
}
