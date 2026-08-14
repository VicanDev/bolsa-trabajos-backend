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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Oferta getOferta() { return oferta; }
    public void setOferta(Oferta oferta) { this.oferta = oferta; }
    public Postulante getPostulante() { return postulante; }
    public void setPostulante(Postulante postulante) { this.postulante = postulante; }
    public LocalDate getFechaPostulacion() { return fechaPostulacion; }
    public void setFechaPostulacion(LocalDate fechaPostulacion) { this.fechaPostulacion = fechaPostulacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}