package com.mh.gestao_plantao.controller.rest;

import com.mh.gestao_plantao.model.Profissional;
import com.mh.gestao_plantao.model.Turno;
import com.mh.gestao_plantao.service.PlantaoService;
import com.mh.gestao_plantao.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class ProfissionalRestController {

    @Autowired
    private ProfissionalService profissionalService;

    @PostMapping("/api/profissionais")
    public ResponseEntity<?> salvar(@RequestBody Profissional profissional) {
        try {
            Profissional salvo = profissionalService.salvar(profissional);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/profissionais/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(profissionalService.buscarPorId(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/api/profissionais/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            profissionalService.deletar(id);
            return ResponseEntity.noContent().build();

        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
