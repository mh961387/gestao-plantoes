package com.mh.gestao_plantao.controller;

import com.mh.gestao_plantao.model.Plantao;
import com.mh.gestao_plantao.model.Profissional;
import com.mh.gestao_plantao.model.Turno;
import com.mh.gestao_plantao.repository.PlantaoRepository;
import com.mh.gestao_plantao.repository.ProfissionalRepository;
import com.mh.gestao_plantao.service.PlantaoService;
import com.mh.gestao_plantao.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class PlantaoController {

    @Autowired
    private PlantaoService plantaoService;

    @Autowired
    private ProfissionalService profissionalService;

    @GetMapping("/plantoes")
    public String tela(
            @RequestParam(required = false) String data,
            @RequestParam(defaultValue = "0") int paginaProf,
            @RequestParam(defaultValue = "0") int paginaPlantao,
            Model model) {

        LocalDate inicio;

        if (data != null && !data.isEmpty()) {
            inicio = LocalDate.parse(data).with(DayOfWeek.MONDAY);
        } else {
            inicio = LocalDate.now().with(DayOfWeek.MONDAY);
        }

        LocalDate fim = inicio.plusDays(6);

        PageRequest pagePlantoes = PageRequest.of(paginaPlantao, 5);

        Page<Plantao> paginaPlantoes =
                plantaoService.listarPorDataComPaginacao(inicio, fim, pagePlantoes);

        PageRequest pageProf = PageRequest.of(paginaProf, 5);

        Page<Profissional> paginaProfissionais =
                profissionalService.listarPaginacao(pageProf);

        List<Profissional> profissionais =
                profissionalService.listar();

        Map<Long, Map<LocalDate, Plantao>> grade = plantaoService.montarGrade(inicio);
        Map<Long, Integer> horasSemana = plantaoService.calcularHorasSemana(inicio);

        model.addAttribute("grade", grade);
        model.addAttribute("inicioSemana", inicio);
        model.addAttribute("fimSemana", fim);
        model.addAttribute("paginaPlantoes", paginaPlantoes);
        model.addAttribute("paginaProfissionais", paginaProfissionais);
        model.addAttribute("profissionais", profissionais);
        model.addAttribute("turnos", Turno.values());
        model.addAttribute("horasSemana", horasSemana);

        return "plantoes";
    }

    @PostMapping("/plantoes")
    public String salvar(
            @RequestParam Long profissionalId,
            @RequestParam String data,
            @RequestParam Turno turno,
            Model model) {
        try {
            plantaoService.salvar(profissionalId, data, turno);
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return tela(null, 0,0,model);
        }
        return "redirect:/plantoes";
    }

    @GetMapping("/deletar-plantao/{id}")
    public String deletar(@PathVariable Long id) {
        plantaoService.deletar(id);
        return "redirect:/plantoes";
    }
}