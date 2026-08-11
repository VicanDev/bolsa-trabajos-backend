package com.bolsa.trabajos.service;

import com.bolsa.trabajos.model.Categoria;
import com.bolsa.trabajos.model.Empleador;
import com.bolsa.trabajos.model.Oferta;
import com.bolsa.trabajos.repository.CategoriaRepository;
import com.bolsa.trabajos.repository.EmpleadorRepository;
import com.bolsa.trabajos.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private EmpleadorRepository empleadorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Oferta crearOferta(Long idEmpleador, Long idCategoria, Oferta oferta) {
        Empleador empleador = empleadorRepository.findById(idEmpleador)
                .orElseThrow(() -> new RuntimeException("Empleador no encontrado"));

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        oferta.setEmpleador(empleador);
        oferta.setCategoria(categoria);
        return ofertaRepository.save(oferta);
    }

    public List<Oferta> listarOfertas() {
        return ofertaRepository.findAll();
    }

    public Oferta obtenerPorId(Long id) {
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
    }

    public List<Oferta> listarPorEmpleador(Long idEmpleador) {
        return ofertaRepository.findByEmpleadorId(idEmpleador);
    }

    public List<Oferta> listarPorCategoria(Long idCategoria) {
        return ofertaRepository.findByCategoriaId(idCategoria);
    }

    public Oferta actualizarOferta(Long id, Oferta datosNuevos) {
        Oferta oferta = obtenerPorId(id);
        oferta.setTitulo(datosNuevos.getTitulo());
        oferta.setDescripcion(datosNuevos.getDescripcion());
        oferta.setUbicacion(datosNuevos.getUbicacion());
        oferta.setDuracion(datosNuevos.getDuracion());
        oferta.setRequisitos(datosNuevos.getRequisitos());
        oferta.setEstado(datosNuevos.getEstado());
        return ofertaRepository.save(oferta);
    }

    public void eliminarOferta(Long id) {
        Oferta oferta = obtenerPorId(id);
        ofertaRepository.delete(oferta);
    }
}