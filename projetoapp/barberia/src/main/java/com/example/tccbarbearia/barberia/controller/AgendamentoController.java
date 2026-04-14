package com.example.tccbarbearia.barberia.controller;

import com.example.tccbarbearia.barberia.dto.AgendamentoRequest;
import com.example.tccbarbearia.barberia.dto.DisponibilidadeResponse;
import com.example.tccbarbearia.barberia.dto.StatusAgendamentoRequest;
import com.example.tccbarbearia.barberia.entity.Agendamento;
import com.example.tccbarbearia.barberia.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@CrossOrigin(origins = "*")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Agendamento> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Agendamento buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Agendamento criar(@Valid @RequestBody AgendamentoRequest request) {
        return service.criar(request);
    }

    @PatchMapping("/{id}/status")
    public Agendamento atualizarStatus(
            @PathVariable Long id,
            @RequestBody StatusAgendamentoRequest request
    ) {
        return service.atualizarStatus(id, request.getStatus());
    }

    @GetMapping("/disponibilidade")
    public List<DisponibilidadeResponse> disponibilidade(
            @RequestParam LocalDate data,
            @RequestParam Long servicoId
    ) {
        return service.consultarDisponibilidade(data, servicoId);
    }
}