package com.example.tccbarbearia.barberia.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoRequest {

    @NotNull
    private Long clienteId;

    @NotNull
    private Long servicoId;

    @NotNull
    @Future
    private LocalDateTime dataHoraInicio;

    private String observacoes;
}