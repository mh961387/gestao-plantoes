package com.mh.gestao_plantao.controller.rest;

import com.mh.gestao_plantao.model.Turno;
import com.mh.gestao_plantao.service.PlantaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plantoes")
public class PlantaoRestController {

    @Autowired
    private PlantaoService plantaoService;

    @PostMapping
    public ResponseEntity<?> salvarPlantao(
            @RequestParam Long profissionalId,
            @RequestParam String data,
            @RequestParam Turno turno) {

        plantaoService.salvar(profissionalId, data, turno);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(plantaoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {

        plantaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
