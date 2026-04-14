package com.example.tccbarbearia.barberia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class DisponibilidadeResponse {
    private LocalTime horario;
    private boolean disponivel;
}