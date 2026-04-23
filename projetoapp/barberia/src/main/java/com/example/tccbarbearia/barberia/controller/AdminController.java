package com.example.tccbarbearia.barberia.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        return Map.of(
                "mensagem", "Área do administrador",
                "agendamentosHoje", 10,
                "faturamentoMes", 2500.00,
                "cortesMes", 42
        );
    }
}