package com.mh.gestao_plantao.service;

import com.mh.gestao_plantao.model.Profissional;
import com.mh.gestao_plantao.repository.PlantaoRepository;
import com.mh.gestao_plantao.repository.ProfissionalRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

public class ProfissionalServiceTest {

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private PlantaoRepository plantaoRepository;

    @InjectMocks
    private ProfissionalService service;

    public ProfissionalServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarProfissional(){
        Profissional profissional = new Profissional();
        profissional.setRegistro("REG123");

        when(profissionalRepository.findByRegistro("REG123"))
                .thenReturn(Optional.empty());

        when(profissionalRepository.save(profissional)).thenReturn(profissional);

        Profissional salvo = service.salvar(profissional);

        assertNotNull(salvo);
        verify(profissionalRepository).save(profissional);
    }

    @Test
    void naoDeveSalvarRegistroDuplicado() {
        Profissional p = new Profissional();
        p.setRegistro("REG123");

        Profissional existente = new Profissional();
        existente.setId(1L);
        existente.setRegistro("REG123");

        when(profissionalRepository.findByRegistro("REG123"))
                .thenReturn(Optional.of(existente));

        assertThrows(IllegalArgumentException.class, () -> {
            service.salvar(p);
        });
    }
}
