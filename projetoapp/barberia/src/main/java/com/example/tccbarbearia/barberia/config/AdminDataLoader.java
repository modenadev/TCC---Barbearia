package com.example.tccbarbearia.barberia.config;

import com.example.tccbarbearia.barberia.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

@Configuration
public class AdminDataLoader {

    @Bean
    public CommandLineRunner loadAdmin(AuthService authService) {
        return args -> authService.criarAdminSeNaoExistir();
    }
}