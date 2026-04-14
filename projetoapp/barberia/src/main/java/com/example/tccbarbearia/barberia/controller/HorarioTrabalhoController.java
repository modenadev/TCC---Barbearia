package com.example.tccbarbearia.barberia.controller;

import com.example.tccbarbearia.barberia.entity.HorarioTrabalho;
import com.example.tccbarbearia.barberia.service.HorarioTrabalhoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horarios-trabalho")
@CrossOrigin(origins = "*")
public class HorarioTrabalhoController {

    private final HorarioTrabalhoService service;

    public HorarioTrabalhoController(HorarioTrabalhoService service) {
        this.service = service;
    }

    @GetMapping
    public List<HorarioTrabalho> listar() {
        return service.listar();
    }

    @PostMapping
    public HorarioTrabalho criar(@RequestBody HorarioTrabalho horario) {
        return service.criar(horario);
    }
}