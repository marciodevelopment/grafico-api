package br.tec.ici.grafico.grafico.service;

import br.tec.ici.grafico.grafico.entity.ErroExecucaoEntity;
import br.tec.ici.grafico.grafico.entity.GraficoEntity;
import br.tec.ici.grafico.grafico.repository.GraficoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GraficoService {

  private final GraficoRepository graficosRepository;
  private final ErroExecucaoService erroExecucaoService;

  public String findJsonByGrafico(String grafico) {
    return this.graficosRepository.findByGrafico(grafico)
        .orElseThrow(
            () -> new EntityNotFoundException("Não foi encontrado cache para " + grafico))
        .getJson();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void salvar(Integer cdGrafico, String json) {
    this.graficosRepository.findById(cdGrafico)
        .map(grafico -> grafico.atualizarJson(json))
        .ifPresent(this.graficosRepository::save);
  }


  public List<GraficoEntity> findAllExecution() {
    return this.graficosRepository.findByDataProximaExecucaoLessThanAndIdAtualizandoFalse(
        LocalDateTime.now());
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void atualizando(Integer cdGrafico) {
    this.graficosRepository.findById(cdGrafico)
        .map(GraficoEntity::atualizando)
        .ifPresent(this.graficosRepository::save);

  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void salvarErro(Integer cdGrafico, Exception e) {
    var grafico = this.graficosRepository.findById(cdGrafico)
        .orElseThrow();
    grafico.finalizarAtualizacao();
    this.erroExecucaoService.save(new ErroExecucaoEntity(grafico, ExceptionUtils.getStackTrace(e)));
  }
}
