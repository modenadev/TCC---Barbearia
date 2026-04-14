package com.example.tccbarbearia.barberia.repository;

import com.example.tccbarbearia.barberia.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByDataHoraInicioBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Agendamento> findByStatusInAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
            List<StatusAgendamento> status,
            LocalDateTime fimNovo,
            LocalDateTime inicioNovo
    );
}