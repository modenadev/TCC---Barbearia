package com.example.tccbarbearia.barberia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.tccbarbearia.barberia.entity.Notificacao;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

List<Notificacao> findByLidaFalseOrderByDataCriacaoDesc();


    
}
