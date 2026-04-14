package com.example.tccbarbearia.barberia.service;

import com.example.tccbarbearia.barberia.entity.Servico;
import com.example.tccbarbearia.barberia.exception.BusinessException;
import com.example.tccbarbearia.barberia.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository repository;

    public ServicoService(ServicoRepository repository) {
        this.repository = repository;
    }

    public List<Servico> listar() {
        return repository.findAll();
    }

    public Servico buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Serviço não encontrado"));
    }

    public Servico criar(Servico servico) {
        return repository.save(servico);
    }

    public Servico atualizar(Long id, Servico dados) {
        Servico servico = buscarPorId(id);
        servico.setNome(dados.getNome());
        servico.setDescricao(dados.getDescricao());
        servico.setPreco(dados.getPreco());
        servico.setDuracaoMinutos(dados.getDuracaoMinutos());
        servico.setAtivo(dados.getAtivo());
        return repository.save(servico);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}