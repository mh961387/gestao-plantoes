package com.mh.gestao_plantao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Registro é obrigatório")
    @Column(nullable = false, unique = true, length = 20)
    private String registro; // CRM para médicos, COREN para enfermagem

    @NotNull(message = "Categoria é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @NotNull(message = "Carga horária é obrigatória")
    @Column(nullable = false)
    private Integer cargaHorariaSemanal;

    public Profissional() {
    }

    public Profissional(Long id, String nome, String registro, Categoria categoria, Integer cargaHorariaSemanal) {
        this.id = id;
        this.nome = nome;
        this.registro = registro;
        this.categoria = categoria;
        this.cargaHorariaSemanal = cargaHorariaSemanal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getCargaHorariaSemanal() {
        return cargaHorariaSemanal;
    }

    public void setCargaHorariaSemanal(Integer cargaHorariaSemanal) {
        this.cargaHorariaSemanal = cargaHorariaSemanal;
    }
}
