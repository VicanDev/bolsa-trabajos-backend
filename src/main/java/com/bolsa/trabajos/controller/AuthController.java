package com.bolsa.trabajos.controller;

import com.bolsa.trabajos.dto.LoginRequest;
import com.bolsa.trabajos.model.Usuario;
import com.bolsa.trabajos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean esValido = usuarioService.verificarCredenciales(loginRequest.getCorreo(), loginRequest.getPassword());

        if (esValido) {
            Usuario usuario = usuarioService.obtenerPorCorreo(loginRequest.getCorreo());
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Login exitoso");
            respuesta.put("id", usuario.getId());
            respuesta.put("nombre", usuario.getNombre());
            respuesta.put("correo", usuario.getCorreo());
            respuesta.put("rol", usuario.getRol());
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Credenciales incorrectas"));
        }
    }
}
