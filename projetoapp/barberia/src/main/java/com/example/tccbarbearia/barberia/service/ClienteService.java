package com.example.tccbarbearia.barberia.service;

import com.example.tccbarbearia.barberia.entity.Cliente;
import com.example.tccbarbearia.barberia.exception.BusinessException;
import com.example.tccbarbearia.barberia.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Cliente não encontrado"));
    }

    public Cliente criar(Cliente cliente) {
        return repository.save(cliente);
    }
}