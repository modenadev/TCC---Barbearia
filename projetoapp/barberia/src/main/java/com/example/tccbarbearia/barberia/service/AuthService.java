package com.example.tccbarbearia.barberia.service;

import com.example.tccbarbearia.barberia.dto.*;
import com.example.tccbarbearia.barberia.entity.*;
import com.example.tccbarbearia.barberia.exception.BusinessException;
import com.example.tccbarbearia.barberia.repository.UsuarioRepository;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

  public AuthResponse registerCliente(RegisterRequest request) {
    if (usuarioRepository.existsByEmail(request.getEmail())) {
        throw new BusinessException("E-mail já cadastrado");
    }

    Usuario usuario = Usuario.builder()
            .nome(request.getNome())
            .email(request.getEmail())
            .senha(passwordEncoder.encode(request.getSenha()))
            .perfil(Perfil.CLIENTE)
            .build();

    usuarioRepository.save(usuario);

    String token = jwtService.generateToken(usuario.getEmail());

    return new AuthResponse(
            token,
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPerfil().name()
    );
}

 public AuthResponse login(LoginRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getSenha()
            )
    );

    Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

    String token = jwtService.generateToken(usuario.getEmail());

    return new AuthResponse(
            token,
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPerfil().name()
    );
}
    public void criarAdminSeNaoExistir() {
        String emailAdmin = "admin@rdbarbearia.com";

        if (!usuarioRepository.existsByEmail(emailAdmin)) {
            Usuario admin = Usuario.builder()
                    .nome("Administrador")
                    .email(emailAdmin)
                    .senha(passwordEncoder.encode("123456"))
                    .perfil(Perfil.ADMIN)
                    .build();

            usuarioRepository.save(admin);
        }
    }
}