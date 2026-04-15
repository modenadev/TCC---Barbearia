package com.example.tccbarbearia.barberia.config;

import com.example.tccbarbearia.barberia.entity.Servico;
import com.example.tccbarbearia.barberia.repository.ServicoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner carregarServicos(ServicoRepository servicoRepository) {
        return args -> {
            if (servicoRepository.count() == 0) {
                servicoRepository.save(
                    Servico.builder()
                        .nome("Corte Masculino")
                        .descricao("Corte tradicional com acabamento na navalha")
                        .preco(new BigDecimal("35.00"))
                        .duracaoMinutos(40)
                        .ativo(true)
                        .build()
                );

                servicoRepository.save(
                    Servico.builder()
                        .nome("Barba")
                        .descricao("Modelagem e alinhamento de barba")
                        .preco(new BigDecimal("20.00"))
                        .duracaoMinutos(20)
                        .ativo(true)
                        .build()
                );

                servicoRepository.save(
                    Servico.builder()
                        .nome("Corte + Barba")
                        .descricao("Pacote completo com corte e barba")
                        .preco(new BigDecimal("50.00"))
                        .duracaoMinutos(60)
                        .ativo(true)
                        .build()
                );

                servicoRepository.save(
                    Servico.builder()
                        .nome("Pigmentação")
                        .descricao("Pigmentação capilar para realce do visual")
                        .preco(new BigDecimal("45.00"))
                        .duracaoMinutos(50)
                        .ativo(true)
                        .build()
                );

                servicoRepository.save(
                    Servico.builder()
                        .nome("Sobrancelha")
                        .descricao("Acabamento e desenho de sobrancelha")
                        .preco(new BigDecimal("15.00"))
                        .duracaoMinutos(15)
                        .ativo(true)
                        .build()
                );
            }
        };
    }
}