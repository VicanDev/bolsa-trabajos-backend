package com.bolsa.trabajos.service;

import com.bolsa.trabajos.model.Postulante;
import com.bolsa.trabajos.model.Usuario;
import com.bolsa.trabajos.repository.PostulanteRepository;
import com.bolsa.trabajos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostulanteService {

    @Autowired
    private PostulanteRepository postulanteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Postulante crearPostulante(Long idUsuario, Postulante postulante) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (postulanteRepository.findByUsuarioId(idUsuario).isPresent()) {
            throw new RuntimeException("Este usuario ya tiene un perfil de postulante");
        }

        postulante.setUsuario(usuario);
        return postulanteRepository.save(postulante);
    }

    public List<Postulante> listarPostulantes() {
        return postulanteRepository.findAll();
    }

    public Postulante obtenerPorId(Long id) {
        return postulanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulante no encontrado"));
    }

    public Postulante obtenerPorUsuario(Long idUsuario) {
        return postulanteRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new RuntimeException("Este usuario no tiene perfil de postulante"));
    }

    public Postulante actualizarPostulante(Long id, Postulante datosNuevos) {
        Postulante postulante = obtenerPorId(id);
        postulante.setCvUrl(datosNuevos.getCvUrl());
        postulante.setHabilidades(datosNuevos.getHabilidades());
        postulante.setDisponibilidad(datosNuevos.getDisponibilidad());
        return postulanteRepository.save(postulante);
    }

    public void eliminarPostulante(Long id) {
        Postulante postulante = obtenerPorId(id);
        postulanteRepository.delete(postulante);
    }
}