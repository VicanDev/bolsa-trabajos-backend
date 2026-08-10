package com.bolsa.trabajos.controller;

import com.bolsa.trabajos.dto.LoginRequest;
import com.bolsa.trabajos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean esValido = usuarioService.verificarCredenciales(loginRequest.getCorreo(), loginRequest.getPassword());

        if (esValido) {
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Login exitoso"));
        } else {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Credenciales incorrectas"));
        }
    }
}
