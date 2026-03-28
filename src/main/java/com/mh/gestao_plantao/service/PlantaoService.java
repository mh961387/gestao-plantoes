package com.mh.gestao_plantao.service;

import com.mh.gestao_plantao.model.Plantao;
import com.mh.gestao_plantao.model.Profissional;
import com.mh.gestao_plantao.model.Turno;
import com.mh.gestao_plantao.repository.PlantaoRepository;
import com.mh.gestao_plantao.repository.ProfissionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PlantaoService {

    @Autowired
    private PlantaoRepository plantaoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    private Logger logger = LoggerFactory.getLogger(PlantaoService.class.getName());

    private Profissional buscarProfissional(Long id){
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Profissional não encontrado"));
    }

    private void validarData(LocalDate data){
        if (data.isBefore(LocalDate.now())){
            throw new RuntimeException("Não é possivel lançar plantão para uma data anterior a de hoje.");
        }
    }

    private void validarPlantaoDuplicado(Profissional profissional, LocalDate data, Turno turno){
        if (plantaoRepository.existsByProfissionalAndDataAndTurno(
                profissional, data, turno)) {
            throw new RuntimeException("Já existe plantão nesse turno");
        }
    }

    private int calcularHorasTurno(Turno turno) {
        return turno == Turno.NOITE ? 12 : 6;
    }

    private int calcularHorasSemanaPorProfissional(Long profissionalId, LocalDate data) {

        LocalDate inicio = data.with(DayOfWeek.MONDAY);
        LocalDate fim = data.with(DayOfWeek.SUNDAY);

        Integer horas = plantaoRepository.somarHorasSemana(profissionalId, inicio, fim);

        return horas != null ? horas : 0;
    }

    private void validarCargaHoraria(Profissional profissional, int horasSemana, int horasNovo) {

        if (horasSemana + horasNovo > profissional.getCargaHorariaSemanal()) {
            throw new RuntimeException("Carga horária excedida");
        }
    }

    public void salvar(Long profissionalId, String dataStr, Turno turno) {

        Profissional profissional = buscarProfissional(profissionalId);

        LocalDate data = LocalDate.parse(dataStr);

        validarData(data);

        validarPlantaoDuplicado(profissional, data, turno);

        int horasSemana = calcularHorasSemanaPorProfissional(profissionalId, data);
        int horasNovo = calcularHorasTurno(turno);

        validarCargaHoraria(profissional, horasSemana, horasNovo);

        Plantao plantao = new Plantao(data, turno, profissional);

        logger.info("Plantão inserido!");
        plantaoRepository.save(plantao);
    }

    public void deletar(Long id) {

        logger.info("Plantão deletado!");
        plantaoRepository.deleteById(id);
    }

    public Map<Long, Map<LocalDate, Plantao>> montarGrade(LocalDate inicio) {

        LocalDate fim = inicio.plusDays(6);

        List<Plantao> plantoes = plantaoRepository.findByDataBetween(inicio, fim);

        Map<Long, Map<LocalDate, Plantao>> grade = new HashMap<>();

        for (Plantao p : plantoes) {

            Long profId = p.getProfissional().getId();

            grade.putIfAbsent(profId, new HashMap<>());
            grade.get(profId).put(p.getData(), p);
        }

        logger.info("Montado a grade com Profissional e sua Data de Plantão!");
        return grade;
    }

    public Map<Long, Integer> calcularHorasSemana(LocalDate inicio) {

        LocalDate fim = inicio.plusDays(6);

        List<Plantao> plantoes = plantaoRepository.findByDataBetween(inicio, fim);

        Map<Long, Integer> horas = new HashMap<>();

        for (Plantao p : plantoes) {

            Long profId = p.getProfissional().getId();

            int carga = (p.getTurno() == Turno.NOITE) ? 12 : 6;

            horas.put(profId, horas.getOrDefault(profId, 0) + carga);
        }
        logger.info("Calculado horas dos plantões marcados");
        return horas;
    }

    public Page<Plantao> listarPorDataComPaginacao(LocalDate inicio, LocalDate fim, PageRequest pageRequestPlantoes) {
        return plantaoRepository.findByDataBetween(inicio, fim, pageRequestPlantoes);
    }
}