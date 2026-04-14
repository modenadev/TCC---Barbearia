package com.example.tccbarbearia.barberia.repository;

import com.example.tccbarbearia.barberia.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioTrabalhoRepository extends JpaRepository<HorarioTrabalho, Long> {
    List<HorarioTrabalho> findByDiaSemanaAndAtivoTrue(Integer diaSemana);
}