package com.mh.gestao_plantao.service;

import com.mh.gestao_plantao.model.*;
import com.mh.gestao_plantao.repository.PlantaoRepository;
import com.mh.gestao_plantao.repository.ProfissionalRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlantaoServiceTest {

    @Mock
    private PlantaoRepository plantaoRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private PlantaoService service;

    public PlantaoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void naoDeveCadastrarPlantaoDuplicado() {

        Profissional prof = new Profissional();
        prof.setId(1L);

        when(profissionalRepository.findById(1L))
                .thenReturn(Optional.of(prof));

        when(plantaoRepository.existsByProfissionalAndDataAndTurno(any(), any(), any()))
                .thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            service.salvar(1L, LocalDate.now().toString(), Turno.MANHA);
        });
    }

    @Test
    void naoDeveCadastrarPlantaoDataPassada() {

        Profissional prof = new Profissional();
        prof.setId(1L);

        when(profissionalRepository.findById(1L))
                .thenReturn(Optional.of(prof));

        assertThrows(RuntimeException.class, () -> {
            service.salvar(1L, "2027-01-01", Turno.MANHA);
        });
    }

    @Test
    void naoDeveCadastrarPlantaoComCargaHorariaMaiorQueCadastrado() {

        Profissional prof = new Profissional();
        prof.setId(1L);
        prof.setCargaHorariaSemanal(5);

        when(profissionalRepository.findById(1L))
                .thenReturn(Optional.of(prof));

        assertThrows(RuntimeException.class, () -> {
            service.salvar(1L, "2027-01-01", Turno.MANHA);
        });
    }
}
