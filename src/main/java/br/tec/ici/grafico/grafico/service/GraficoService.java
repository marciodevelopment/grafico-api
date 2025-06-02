package br.tec.ici.grafico.grafico.service;

import br.tec.ici.grafico.grafico.repository.GraficoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GraficoService {

  private final GraficoRepository graficosRepository;

  public String findJsonByNmCache(String nmGrafico) {
    return this.graficosRepository.findByNmGrafico(nmGrafico)
        .orElseThrow(
            () -> new EntityNotFoundException("Não foi encontrado cache para " + nmGrafico))
        .getJson();
  }

  @Transactional
  public void salvar(Integer cdGrafico, String json) {
    this.graficosRepository.findById(cdGrafico)
        .map(grafico -> grafico.atualizarJson(json))
        .ifPresent(this.graficosRepository::save);
  }
}
