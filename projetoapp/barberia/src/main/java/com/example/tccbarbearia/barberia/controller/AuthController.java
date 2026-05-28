package com.example.tccbarbearia.barberia.controller;

import com.example.tccbarbearia.barberia.dto.*;
import com.example.tccbarbearia.barberia.service.AuthService;
import com.example.tccbarbearia.barberia.service.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService service;
    private final UsuarioService usuarioService;

    public AuthController(AuthService service, UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;   
    }

    @PostMapping("/register-cliente")
    public AuthResponse registerCliente(@Valid @RequestBody RegisterRequest request) {
        return service.registerCliente(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<Void> esqueciSenha(@RequestBody EsqueciSenhaRequest request) {
        usuarioService.esqueciMinhaSenha(request.getEmail());
        return ResponseEntity.ok().build();
    }
}