package com.example.tccbarbearia.barberia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.tccbarbearia.barberia.entity.Notificacao;

import com.example.tccbarbearia.barberia.repository.NotificacaoRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/notificacoes")
@CrossOrigin(origins = "*")
public class NotificacaoController {

    @Autowired
    private NotificacaoRepository notificacaoRepository;


    @GetMapping("/nao-lidas")
    public List<Notificacao> getNotificacoesNaoLidas() {
        return notificacaoRepository.findByLidaFalseOrderByDataCriacaoDesc();
        
    }


    @PatchMapping("/{id}/marcar-como-lida")
    public Notificacao marcarComoLida(@PathVariable Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada com id: " + id));
        notificacao.setLida(true);
        return notificacaoRepository.save(notificacao);
    }
    
    @PatchMapping("/marcar-todas-como-lidas")
    public List<Notificacao> marcarTodasComoLidas() {
        List<Notificacao> notificacoes = notificacaoRepository.findByLidaFalseOrderByDataCriacaoDesc();
        for (Notificacao notificacao : notificacoes) {
            notificacao.setLida(true);
        }
        return notificacaoRepository.saveAll(notificacoes);
    }


    
}
