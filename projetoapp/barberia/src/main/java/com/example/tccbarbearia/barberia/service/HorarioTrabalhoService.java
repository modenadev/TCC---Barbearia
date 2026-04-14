package com.example.tccbarbearia.barberia.service;

import com.example.tccbarbearia.barberia.entity.HorarioTrabalho;
import com.example.tccbarbearia.barberia.repository.HorarioTrabalhoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioTrabalhoService {

    private final HorarioTrabalhoRepository repository;

    public HorarioTrabalhoService(HorarioTrabalhoRepository repository) {
        this.repository = repository;
    }

    public List<HorarioTrabalho> listar() {
        return repository.findAll();
    }

    public HorarioTrabalho criar(HorarioTrabalho horario) {
        return repository.save(horario);
    }
}