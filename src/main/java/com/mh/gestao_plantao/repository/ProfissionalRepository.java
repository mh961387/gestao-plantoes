package com.mh.gestao_plantao.repository;

import com.mh.gestao_plantao.model.Categoria;
import com.mh.gestao_plantao.model.Profissional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    Page<Profissional> findByCategoria(Categoria categoria, Pageable pageable);
    Optional<Profissional> findByRegistro(String registro);
}
