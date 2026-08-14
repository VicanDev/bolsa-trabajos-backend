package com.bolsa.trabajos.service;

import com.bolsa.trabajos.model.Empleador;
import com.bolsa.trabajos.model.Usuario;
import com.bolsa.trabajos.repository.EmpleadorRepository;
import com.bolsa.trabajos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadorService {

    @Autowired
    private EmpleadorRepository empleadorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Empleador crearEmpleador(Long idUsuario, Empleador empleador) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (empleadorRepository.findByUsuarioId(idUsuario).isPresent()) {
            throw new RuntimeException("Este usuario ya tiene un perfil de empleador");
        }

        empleador.setUsuario(usuario);
        return empleadorRepository.save(empleador);
    }

    public List<Empleador> listarEmpleadores() {
        return empleadorRepository.findAll();
    }

    public Empleador obtenerPorId(Long id) {
        return empleadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleador no encontrado"));
    }

    public Empleador obtenerPorUsuario(Long idUsuario) {
        return empleadorRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new RuntimeException("Este usuario no tiene perfil de empleador"));
    }

    public Empleador actualizarEmpleador(Long id, Empleador datosNuevos) {
        Empleador empleador = obtenerPorId(id);
        empleador.setRazonSocial(datosNuevos.getRazonSocial());
        empleador.setRuc(datosNuevos.getRuc());
        empleador.setDescripcion(datosNuevos.getDescripcion());
        return empleadorRepository.save(empleador);
    }

    public void eliminarEmpleador(Long id) {
        Empleador empleador = obtenerPorId(id);
        empleadorRepository.delete(empleador);
    }
}