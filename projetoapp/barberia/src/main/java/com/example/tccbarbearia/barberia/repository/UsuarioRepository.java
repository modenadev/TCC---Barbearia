package com.example.tccbarbearia.barberia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tccbarbearia.barberia.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
   Optional <Usuario> findByEmail(String email);
    boolean existsByEmail(String email);




    
}
