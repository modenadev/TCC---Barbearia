package com.example.tccbarbearia.barberia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String nome;
    private String email;
    private String perfil;
    private String senha;
    
}
