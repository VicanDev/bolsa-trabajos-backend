package com.bolsa.trabajos.service;

import com.bolsa.trabajos.model.Usuario;
import com.bolsa.trabajos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }
        
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public boolean verificarCredenciales(String correo, String password) {
        Optional<Usuario> optUsuario = usuarioRepository.findByCorreo(correo);
        
        if (optUsuario.isPresent()) {
            Usuario usuario = optUsuario.get();
            return passwordEncoder.matches(password, usuario.getPassword());
        }
        return false;
    }
}
