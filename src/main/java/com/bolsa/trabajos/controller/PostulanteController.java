package com.bolsa.trabajos.controller;

import com.bolsa.trabajos.model.Postulante;
import com.bolsa.trabajos.service.PostulanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postulantes")
public class PostulanteController {

    @Autowired
    private PostulanteService postulanteService;

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> crearPostulante(@PathVariable Long idUsuario, @RequestBody Postulante postulante) {
        try {
            Postulante nuevo = postulanteService.crearPostulante(idUsuario, postulante);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Postulante>> listarPostulantes() {
        return ResponseEntity.ok(postulanteService.listarPostulantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPostulante(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(postulanteService.obtenerPorId(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Long idUsuario) {
        try {
            return ResponseEntity.ok(postulanteService.obtenerPorUsuario(idUsuario));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPostulante(@PathVariable Long id, @RequestBody Postulante postulante) {
        try {
            return ResponseEntity.ok(postulanteService.actualizarPostulante(id, postulante));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPostulante(@PathVariable Long id) {
        try {
            postulanteService.eliminarPostulante(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }
}