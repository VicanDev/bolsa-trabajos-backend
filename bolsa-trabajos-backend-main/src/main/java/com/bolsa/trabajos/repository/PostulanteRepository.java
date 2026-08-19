package com.bolsa.trabajos.repository;

import com.bolsa.trabajos.model.Postulante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostulanteRepository extends JpaRepository<Postulante, Long> {
    Optional<Postulante> findByUsuarioId(Long idUsuario);
}