package com.mh.gestao_plantao.controller.rest;

import com.mh.gestao_plantao.model.Profissional;
import com.mh.gestao_plantao.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalRestController {

    @Autowired
    private ProfissionalService profissionalService;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Profissional profissional) {

        Profissional salvo = profissionalService.salvar(profissional);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        return ResponseEntity.ok(profissionalService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {

        profissionalService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
