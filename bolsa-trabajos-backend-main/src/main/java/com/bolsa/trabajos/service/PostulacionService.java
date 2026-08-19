package com.bolsa.trabajos.service;

import com.bolsa.trabajos.dto.DatoDTO;
import com.bolsa.trabajos.exception.ResourceNotFoundException;
import com.bolsa.trabajos.model.Oferta;
import com.bolsa.trabajos.model.Postulacion;
import com.bolsa.trabajos.model.Postulante;
import com.bolsa.trabajos.model.Usuario;
import com.bolsa.trabajos.repository.OfertaRepository;
import com.bolsa.trabajos.repository.PostulacionRepository;
import com.bolsa.trabajos.repository.PostulanteRepository;
import com.bolsa.trabajos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostulacionService {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private PostulanteRepository postulanteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Postulacion crearPostulacion(Long idOferta, Long idPostulante, String correoSolicitante) {
        Oferta oferta = ofertaRepository.findById(idOferta)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta no encontrada"));

        Postulante postulante = postulanteRepository.findById(idPostulante)
                .orElseThrow(() -> new ResourceNotFoundException("Postulante no encontrado"));

        if (!postulante.getUsuario().getCorreo().equalsIgnoreCase(correoSolicitante)) {
            throw new AccessDeniedException("Solo puedes postular en tu propio nombre");
        }

        Postulacion postulacion = new Postulacion();
        postulacion.setOferta(oferta);
        postulacion.setPostulante(postulante);
        return postulacionRepository.save(postulacion);
    }

    public List<Postulacion> listarPostulaciones() {
        return postulacionRepository.findAll();
    }

    public Postulacion obtenerPorId(Long id) {
        return postulacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Postulación no encontrada"));
    }

    public List<Postulacion> listarMisPostulaciones(String correoSolicitante) {
        Usuario usuario = usuarioRepository.findByCorreo(correoSolicitante)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Postulante postulante = postulanteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Este usuario no tiene perfil de postulante"));

        return postulacionRepository.findByPostulanteId(postulante.getId());
    }

    public List<Postulacion> listarPorPostulante(Long idPostulante, String correoSolicitante) {
        Postulante postulante = postulanteRepository.findById(idPostulante)
                .orElseThrow(() -> new ResourceNotFoundException("Postulante no encontrado"));

        if (!postulante.getUsuario().getCorreo().equalsIgnoreCase(correoSolicitante)) {
            throw new AccessDeniedException("Solo puedes ver tus propias postulaciones");
        }

        return postulacionRepository.findByPostulanteId(idPostulante);
    }

    public List<DatoDTO> listarPorOferta(Long idOferta, String correoSolicitante) {
        Oferta oferta = ofertaRepository.findById(idOferta)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta no encontrada"));

        if (!oferta.getEmpleador().getUsuario().getCorreo().equalsIgnoreCase(correoSolicitante)) {
            throw new AccessDeniedException("Solo puedes ver las postulaciones de tus propias ofertas");
        }

        return postulacionRepository.findByOfertaId(idOferta).stream()
                .map(this::convertirADato)
                .collect(Collectors.toList());
    }

    private DatoDTO convertirADato(Postulacion postulacion) {
        Postulante postulante = postulacion.getPostulante();
        DatoDTO dato = new DatoDTO();
        dato.setIdPostulacion(postulacion.getId());
        dato.setNombre(postulante.getUsuario().getNombre());
        dato.setCorreo(postulante.getUsuario().getCorreo());
        dato.setHabilidades(postulante.getHabilidades());
        dato.setDisponibilidad(postulante.getDisponibilidad());
        dato.setCvUrl(postulante.getCvUrl());
        dato.setEstado(postulacion.getEstado());
        return dato;
    }

    public Postulacion actualizarEstado(Long id, String nuevoEstado, String correoSolicitante) {
        Postulacion postulacion = obtenerPorId(id);

        if (!postulacion.getOferta().getEmpleador().getUsuario().getCorreo().equalsIgnoreCase(correoSolicitante)) {
            throw new AccessDeniedException("Solo el empleador dueño de la oferta puede cambiar el estado");
        }

        postulacion.setEstado(nuevoEstado.toUpperCase());
        return postulacionRepository.save(postulacion);
    }

    public void eliminarPostulacion(Long id) {
        Postulacion postulacion = obtenerPorId(id);
        postulacionRepository.delete(postulacion);
    }
}
