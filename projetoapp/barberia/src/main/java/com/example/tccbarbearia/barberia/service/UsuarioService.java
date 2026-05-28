package com.example.tccbarbearia.barberia.service;

import com.example.tccbarbearia.barberia.dto.*;
import com.example.tccbarbearia.barberia.entity.Cliente;
import com.example.tccbarbearia.barberia.entity.Usuario;
import com.example.tccbarbearia.barberia.exception.BusinessException;
import com.example.tccbarbearia.barberia.repository.ClienteRepository;
import com.example.tccbarbearia.barberia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WhatsAppService whatsAppService;

    private Usuario getUsuarioLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
    }

    public AtualizarDadosRequest obterMeusDados() {
        Usuario usuario = getUsuarioLogado();
        AtualizarDadosRequest dados = new AtualizarDadosRequest();
        dados.setNome(usuario.getNome());
        dados.setEmail(usuario.getEmail());

        clienteRepository.findByUsuario(usuario).ifPresent(cliente -> {
            dados.setTelefone(cliente.getTelefone());
        });

        return dados;
    }

    @Transactional
    public void atualizarMeusDados(AtualizarDadosRequest request) {
        Usuario usuario = getUsuarioLogado();
        
        if (!usuario.getEmail().equals(request.getEmail()) && usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("Este e-mail já está em uso.");
        }

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuarioRepository.save(usuario);

        clienteRepository.findByUsuario(usuario).ifPresent(cliente -> {
            cliente.setNome(request.getNome());
            cliente.setEmail(request.getEmail());
            cliente.setTelefone(request.getTelefone());
            clienteRepository.save(cliente);
        });
    }

    public void alterarSenha(AlterarSenhaRequest request) {
        Usuario usuario = getUsuarioLogado();

        if (!passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenha())) {
            throw new BusinessException("A senha atual informada está incorreta.");
        }

        usuario.setSenha(passwordEncoder.encode(request.getNovaSenha()));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void esqueciMinhaSenha(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Nenhuma conta encontrada com este e-mail."));

        String senhaProvisoria = String.format("%06d", new Random().nextInt(999999));

        usuario.setSenha(passwordEncoder.encode(senhaProvisoria));
        usuarioRepository.save(usuario);

        clienteRepository.findByUsuario(usuario).ifPresent(cliente -> {
            String mensagem = "💈 *RD Barbearia*\n\n" +
                    "Olá " + cliente.getNome() + "!\n\n" +
                    "Recebemos um pedido de recuperação de senha. Sua senha provisória é:\n\n" +
                    "👉 *" + senhaProvisoria + "*\n\n" +
                    "Use esta senha para fazer login e, em seguida, vá em 'Meus Dados' para cadastrar uma nova senha de sua preferência.";
            
            whatsAppService.enviarMensagem(cliente.getTelefone(), mensagem);
        });
    }
}