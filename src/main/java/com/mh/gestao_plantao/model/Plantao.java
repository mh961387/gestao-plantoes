package com.mh.gestao_plantao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.annotation.EnumNaming;

import java.time.LocalDate;

@Entity
public class Plantao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    private Turno turno;

    @ManyToOne
    private Profissional profissional;
}
