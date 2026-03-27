package com.mh.gestao_plantao.controller;

import com.mh.gestao_plantao.model.Categoria;
import com.mh.gestao_plantao.model.Profissional;
import com.mh.gestao_plantao.service.ProfissionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfissionalController {

    @Autowired
    private ProfissionalService service;

    @GetMapping("/profissionais")
    public String listar(
            @RequestParam(defaultValue = "0") int paginacao,
            @RequestParam(required = false) Categoria categoria,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(paginacao, 5);
        Page<Profissional> page;

        if (categoria != null) {
            page = service.listarPorCategoria(categoria, pageRequest);
        } else {
            page = service.listarPaginacao(pageRequest);
        }

        model.addAttribute("pagina", page);
        model.addAttribute("categorias", Categoria.values()); // corrigido

        return "profissionais";
    }

    @GetMapping("/cadastro-profissional")
    public String form(Model model) {
        model.addAttribute("profissional", new Profissional());
        model.addAttribute("categorias", Categoria.values());
        return "cadastro-profissional";
    }

    @PostMapping("/profissionais")
    public String salvar(@Valid Profissional profissional,
                         BindingResult result,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categorias", Categoria.values());
            return "cadastro-profissional";
        }

        try {
            service.salvar(profissional);
        } catch (RuntimeException e) {
            result.rejectValue("registro", "erro.registro", e.getMessage());
            model.addAttribute("categorias", Categoria.values());
            return "cadastro-profissional";
        }

        return "redirect:/profissionais";
    }

    @GetMapping("/editar-profissional/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Profissional profissional = service.buscarPorId(id);

        model.addAttribute("profissional", profissional);
        model.addAttribute("categorias", Categoria.values());

        return "cadastro-profissional";
    }

    @GetMapping("/deletar-profissional/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/profissionais";
    }
}
