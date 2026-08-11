package com.bolsa.trabajos.repository;

import com.bolsa.trabajos.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    List<Oferta> findByEmpleadorId(Long idEmpleador);
    List<Oferta> findByCategoriaId(Long idCategoria);
}