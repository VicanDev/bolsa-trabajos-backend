package com.bolsa.trabajos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "postulaciones")
public class Postulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    private Oferta oferta;

    @ManyToOne
    @JoinColumn(name = "id_postulante", nullable = false)
    private Postulante postulante;

    @Column(name = "fecha_postulacion")
    private LocalDate fechaPostulacion;

    private String estado;

    @PrePersist
    public void prePersist() {
        if (fechaPostulacion == null) {
            fechaPostulacion = LocalDate.now();
        }
        if (estado == null) {
            estado = "PENDIENTE";
        }
    }
}