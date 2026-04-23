package com.example.tccbarbearia.barberia.controller;

import com.example.tccbarbearia.barberia.dto.*;
import com.example.tccbarbearia.barberia.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register-cliente")
    public AuthResponse registerCliente(@Valid @RequestBody RegisterRequest request) {
        return service.registerCliente(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }
}