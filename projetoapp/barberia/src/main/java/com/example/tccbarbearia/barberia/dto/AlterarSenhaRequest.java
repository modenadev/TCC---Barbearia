package com.example.tccbarbearia.barberia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarSenhaRequest {

    private String senhaAtual;
    private String novaSenha;
    
}
