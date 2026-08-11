package com.bolsa.trabajos.repository;

import com.bolsa.trabajos.model.Empleador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadorRepository extends JpaRepository<Empleador, Long> {
    Optional<Empleador> findByUsuarioId(Long idUsuario);
}