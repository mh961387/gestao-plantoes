package com.mh.gestao_plantao.service;

import com.mh.gestao_plantao.model.Categoria;
import com.mh.gestao_plantao.model.Profissional;
import com.mh.gestao_plantao.repository.PlantaoRepository;
import com.mh.gestao_plantao.repository.ProfissionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProfissionalService {

    private Logger logger = LoggerFactory.getLogger(ProfissionalService.class.getName());

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private PlantaoRepository plantaoRepository;

    private void validarExclusaoComPlantao(Long id){
        if (plantaoRepository.existsByProfissionalId(id)){
            throw new IllegalStateException("Profissional não pode ser excluido pois existe plantões!");
        }
    }

    private void validarReducaoCargaHoraria(Profissional profissional){

        Profissional atual = buscarPorId(profissional.getId());

        boolean temPlantao = plantaoRepository.existsByProfissionalId(profissional.getId());

        boolean reduziuCarga = profissional.getCargaHorariaSemanal() < atual.getCargaHorariaSemanal();

        if (temPlantao && reduziuCarga) {
            throw new IllegalStateException("Não é possível diminuir carga horária de um profissional com plantão!");
        }
    }

    private void validarRegistroDuplicado(Profissional profissional){

        Optional<Profissional> existente =
                profissionalRepository.findByRegistro(profissional.getRegistro());

        if (existente.isPresent() &&
                !existente.get().getId().equals(profissional.getId())) {

            throw new IllegalArgumentException("Registro já cadastrado");
        }
    }

    public Profissional salvar(Profissional profissional){

        validarRegistroDuplicado(profissional);

        if (profissional.getId() != null) {
            validarReducaoCargaHoraria(profissional);
        }

        logger.info("Profissional salvo!");
        return profissionalRepository.save(profissional);
    }

    public Page<Profissional> listarPorCategoria(Categoria categoria, Pageable pageable) {
        return profissionalRepository.findByCategoria(categoria, pageable);
    }

    public Page<Profissional> listarPaginacao(Pageable pageable){

        logger.info("Profissionais listados por paginação!");
        return profissionalRepository.findAll(pageable);
    }

    public List<Profissional> listar(){

        logger.info("Profissionais listados!");
        return profissionalRepository.findAll();
    }

    public Profissional buscarPorId(Long id){

        logger.info("Busca de profissionais por ID!");
        return profissionalRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Profissional não encontrado"));
    }

    public void deletar(Long id){
        validarExclusaoComPlantao(id);
        logger.info("Profissional deletado!");
        profissionalRepository.deleteById(id);
    }
}
