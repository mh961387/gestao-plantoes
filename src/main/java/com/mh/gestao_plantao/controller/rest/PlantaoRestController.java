package com.mh.gestao_plantao.controller.rest;

import com.mh.gestao_plantao.model.Turno;
import com.mh.gestao_plantao.service.PlantaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class PlantaoRestController {

    @Autowired
    private PlantaoService plantaoService;

    @PostMapping("/api/plantoes")
    public ResponseEntity<?> salvarPlantao(
            @RequestParam Long profissionalId,
            @RequestParam String data,
            @RequestParam Turno turno) {

        try {
            plantaoService.salvar(profissionalId, data, turno);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/plantoes/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(plantaoService.buscarPorId(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/api/plantoes/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            plantaoService.deletar(id);
            return ResponseEntity.noContent().build();

        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
