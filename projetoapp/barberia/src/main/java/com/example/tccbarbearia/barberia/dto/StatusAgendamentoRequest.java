package com.example.tccbarbearia.barberia.dto;

import com.example.tccbarbearia.barberia.entity.StatusAgendamento;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusAgendamentoRequest {

    @NotNull
    private StatusAgendamento status;
}