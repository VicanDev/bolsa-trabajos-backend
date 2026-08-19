package com.bolsa.trabajos.service;

import com.bolsa.trabajos.exception.ResourceNotFoundException;
import com.bolsa.trabajos.model.Categoria;
import com.bolsa.trabajos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
    }

    public Categoria actualizarCategoria(Long id, Categoria datosNuevos) {
        Categoria categoria = obtenerPorId(id);
        categoria.setNombre(datosNuevos.getNombre());
        return categoriaRepository.save(categoria);
    }

    public void eliminarCategoria(Long id) {
        Categoria categoria = obtenerPorId(id);
        categoriaRepository.delete(categoria);
    }
}
