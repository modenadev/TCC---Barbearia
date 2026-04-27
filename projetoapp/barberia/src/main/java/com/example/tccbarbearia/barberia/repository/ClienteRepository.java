package com.example.tccbarbearia.barberia.repository;

import com.example.tccbarbearia.barberia.entity.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByUsuario(Usuario usuario);
}