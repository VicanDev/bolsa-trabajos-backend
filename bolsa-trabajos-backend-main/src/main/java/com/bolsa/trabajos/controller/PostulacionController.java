package com.bolsa.trabajos.controller;

import com.bolsa.trabajos.dto.CambiarEstadoRequest;
import com.bolsa.trabajos.dto.DatoDTO;
import com.bolsa.trabajos.model.Postulacion;
import com.bolsa.trabajos.service.PostulacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postulaciones")
public class PostulacionController {

    @Autowired
    private PostulacionService postulacionService;

    private String correoAutenticado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PreAuthorize("hasRole('POSTULANTE')")
    @PostMapping("/oferta/{idOferta}/postulante/{idPostulante}")
    public ResponseEntity<?> crearPostulacion(@PathVariable Long idOferta, @PathVariable Long idPostulante) {
        Postulacion nueva = postulacionService.crearPostulacion(idOferta, idPostulante, correoAutenticado());
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('EMPLEADOR')")
    @GetMapping
    public ResponseEntity<List<Postulacion>> listarPostulaciones() {
        return ResponseEntity.ok(postulacionService.listarPostulaciones());
    }

    @PreAuthorize("hasRole('EMPLEADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPostulacion(@PathVariable Long id) {
        return ResponseEntity.ok(postulacionService.obtenerPorId(id));
    }

    @PreAuthorize("hasRole('POSTULANTE')")
    @GetMapping("/mis-postulaciones")
    public ResponseEntity<?> listarMisPostulaciones() {
        return ResponseEntity.ok(postulacionService.listarMisPostulaciones(correoAutenticado()));
    }

    @PreAuthorize("hasRole('POSTULANTE')")
    @GetMapping("/postulante/{idPostulante}")
    public ResponseEntity<?> listarPorPostulante(@PathVariable Long idPostulante) {
        return ResponseEntity.ok(postulacionService.listarPorPostulante(idPostulante, correoAutenticado()));
    }

    @PreAuthorize("hasRole('EMPLEADOR')")
    @GetMapping("/oferta/{idOferta}")
    public ResponseEntity<List<DatoDTO>> listarPorOferta(@PathVariable Long idOferta) {
        return ResponseEntity.ok(postulacionService.listarPorOferta(idOferta, correoAutenticado()));
    }

    @PreAuthorize("hasRole('EMPLEADOR')")
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @Valid @RequestBody CambiarEstadoRequest body) {
        return ResponseEntity.ok(postulacionService.actualizarEstado(id, body.getEstado(), correoAutenticado()));
    }

    @PreAuthorize("hasRole('EMPLEADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPostulacion(@PathVariable Long id) {
        postulacionService.eliminarPostulacion(id);
        return ResponseEntity.noContent().build();
    }
}
