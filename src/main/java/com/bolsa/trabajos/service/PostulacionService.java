package com.bolsa.trabajos.service;

import com.bolsa.trabajos.model.Oferta;
import com.bolsa.trabajos.model.Postulacion;
import com.bolsa.trabajos.model.Postulante;
import com.bolsa.trabajos.repository.OfertaRepository;
import com.bolsa.trabajos.repository.PostulacionRepository;
import com.bolsa.trabajos.repository.PostulanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostulacionService {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private PostulanteRepository postulanteRepository;

    public Postulacion crearPostulacion(Long idOferta, Long idPostulante) {
        Oferta oferta = ofertaRepository.findById(idOferta)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        Postulante postulante = postulanteRepository.findById(idPostulante)
                .orElseThrow(() -> new RuntimeException("Postulante no encontrado"));

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
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));
    }

    public List<Postulacion> listarPorPostulante(Long idPostulante) {
        return postulacionRepository.findByPostulanteId(idPostulante);
    }

    public List<Postulacion> listarPorOferta(Long idOferta) {
        return postulacionRepository.findByOfertaId(idOferta);
    }

    public Postulacion actualizarEstado(Long id, String nuevoEstado) {
        Postulacion postulacion = obtenerPorId(id);
        postulacion.setEstado(nuevoEstado);
        return postulacionRepository.save(postulacion);
    }

    public void eliminarPostulacion(Long id) {
        Postulacion postulacion = obtenerPorId(id);
        postulacionRepository.delete(postulacion);
    }
}