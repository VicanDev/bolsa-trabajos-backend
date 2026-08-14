package com.bolsa.trabajos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "empleadores")
public class Empleador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(nullable = false, unique = true)
    private String ruc;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}