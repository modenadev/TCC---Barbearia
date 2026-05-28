package com.example.tccbarbearia.barberia.controller;

import com.example.tccbarbearia.barberia.dto.AlterarSenhaRequest;
import com.example.tccbarbearia.barberia.dto.AtualizarDadosRequest;
import com.example.tccbarbearia.barberia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/meus-dados")
    public ResponseEntity<AtualizarDadosRequest> obterMeusDados() {
        return ResponseEntity.ok(usuarioService.obterMeusDados());
    }

    @PutMapping("/meus-dados")
    public ResponseEntity<Void> atualizarMeusDados(@RequestBody AtualizarDadosRequest request) {
        usuarioService.atualizarMeusDados(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/alterar-senha")
    public ResponseEntity<Void> alterarSenha(@RequestBody AlterarSenhaRequest request) {
        usuarioService.alterarSenha(request);
        return ResponseEntity.ok().build();
    }
}