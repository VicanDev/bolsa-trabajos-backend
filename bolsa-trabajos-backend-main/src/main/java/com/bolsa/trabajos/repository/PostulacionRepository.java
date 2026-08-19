package com.bolsa.trabajos.repository;

import com.bolsa.trabajos.model.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {
    List<Postulacion> findByPostulanteId(Long idPostulante);
    List<Postulacion> findByOfertaId(Long idOferta);
}