package com.bolsa.trabajos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ofertas")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_empleador", nullable = false)
    private Empleador empleador;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String ubicacion;

    private String duracion;

    @Column(columnDefinition = "TEXT")
    private String requisitos;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    private String estado;

    @PrePersist
    public void prePersist() {
        if (fechaPublicacion == null) {
            fechaPublicacion = LocalDate.now();
        }
        if (estado == null) {
            estado = "ACTIVA";
        }
    }
}