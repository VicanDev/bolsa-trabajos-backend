package com.bolsa.trabajos.controller;

import com.bolsa.trabajos.model.Postulacion;
import com.bolsa.trabajos.service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/postulaciones")
public class PostulacionController {

    @Autowired
    private PostulacionService postulacionService;

    @PostMapping("/oferta/{idOferta}/postulante/{idPostulante}")
    public ResponseEntity<?> crearPostulacion(@PathVariable Long idOferta, @PathVariable Long idPostulante) {
        try {
            Postulacion nueva = postulacionService.crearPostulacion(idOferta, idPostulante);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Postulacion>> listarPostulaciones() {
        return ResponseEntity.ok(postulacionService.listarPostulaciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPostulacion(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(postulacionService.obtenerPorId(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    @GetMapping("/postulante/{idPostulante}")
    public ResponseEntity<List<Postulacion>> listarPorPostulante(@PathVariable Long idPostulante) {
        return ResponseEntity.ok(postulacionService.listarPorPostulante(idPostulante));
    }

    @GetMapping("/oferta/{idOferta}")
    public ResponseEntity<List<Postulacion>> listarPorOferta(@PathVariable Long idOferta) {
        return ResponseEntity.ok(postulacionService.listarPorOferta(idOferta));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String nuevoEstado = body.get("estado");
            return ResponseEntity.ok(postulacionService.actualizarEstado(id, nuevoEstado));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPostulacion(@PathVariable Long id) {
        try {
            postulacionService.eliminarPostulacion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }
}