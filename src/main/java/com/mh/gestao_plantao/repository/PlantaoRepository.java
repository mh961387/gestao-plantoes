package com.mh.gestao_plantao.repository;

import com.mh.gestao_plantao.model.Plantao;
import com.mh.gestao_plantao.model.Profissional;
import com.mh.gestao_plantao.model.Turno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PlantaoRepository extends JpaRepository<Plantao, Long> {
    boolean existsByProfissionalAndDataAndTurno(
            Profissional profissional,
            LocalDate data,
            Turno turno
    );
    List<Plantao> findByDataBetween(LocalDate inicio, LocalDate fim);

    Page<Plantao> findByDataBetween(LocalDate inicio, LocalDate fim, Pageable pageable);

    Boolean existsByProfissionalId(Long id);

    @Query("""
            SELECT SUM(CASE WHEN p.turno = 2 THEN 12 ELSE 6 END)
            FROM Plantao p
            WHERE p.profissional.id = :profissionalId
            AND p.data BETWEEN :inicio AND :fim
            """)
    Integer somarHorasSemana(Long profissionalId, LocalDate inicio, LocalDate fim);

}
