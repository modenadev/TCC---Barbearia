package com.example.tccbarbearia.barberia.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cliente")
public class ClienteAreaController {

    @GetMapping("/perfil")
    public Map<String, Object> perfil() {
        return Map.of(
                "mensagem", "Área do cliente"
        );
    }
}