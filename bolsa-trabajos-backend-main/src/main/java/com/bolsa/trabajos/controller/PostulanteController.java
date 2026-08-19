package com.bolsa.trabajos.controller;

import com.bolsa.trabajos.model.Postulante;
import com.bolsa.trabajos.service.PostulanteService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> crearPostulante(@PathVariable Long idUsuario, @Valid @RequestBody Postulante postulante) {
        Postulante nuevo = postulanteService.crearPostulante(idUsuario, postulante);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Postulante>> listarPostulantes() {
        return ResponseEntity.ok(postulanteService.listarPostulantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPostulante(@PathVariable Long id) {
        return ResponseEntity.ok(postulanteService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(postulanteService.obtenerPorUsuario(idUsuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPostulante(@PathVariable Long id, @Valid @RequestBody Postulante postulante) {
        return ResponseEntity.ok(postulanteService.actualizarPostulante(id, postulante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPostulante(@PathVariable Long id) {
        postulanteService.eliminarPostulante(id);
        return ResponseEntity.noContent().build();
    }
}
