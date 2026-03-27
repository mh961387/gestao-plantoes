package com.mh.gestao_plantao.repository;

import com.mh.gestao_plantao.model.Plantao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantaoRepository extends JpaRepository<Plantao, Long> {
}
