package com.mh.gestao_plantao.service;

import com.mh.gestao_plantao.model.Categoria;
import com.mh.gestao_plantao.model.Profissional;
import com.mh.gestao_plantao.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository repository;

    public Profissional salvar(Profissional profissional){
        Optional<Profissional> existente =
                repository.findByRegistro(profissional.getRegistro());

        if (existente.isPresent() &&
                !existente.get().getId().equals(profissional.getId())) {

            throw new RuntimeException("Registro já cadastrado");
        }

        return repository.save(profissional);
    }

    public Page<Profissional> listarPorCategoria(Categoria categoria, Pageable pageable) {
        return repository.findByCategoria(categoria, pageable);
    }

    public Page<Profissional> listarPaginacao(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Profissional buscarPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }
}
