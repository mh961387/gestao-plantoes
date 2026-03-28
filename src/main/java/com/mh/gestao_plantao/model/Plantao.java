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

    public Plantao() {
    }

    public Plantao(LocalDate data, Turno turno, Profissional profissional) {
        this.data = data;
        this.turno = turno;
        this.profissional = profissional;
    }

    public Plantao(Long id, LocalDate data, Turno turno, Profissional profissional) {
        this.id = id;
        this.data = data;
        this.turno = turno;
        this.profissional = profissional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }
}
