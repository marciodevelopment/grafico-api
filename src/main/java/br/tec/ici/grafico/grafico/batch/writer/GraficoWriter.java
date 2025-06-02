package br.tec.ici.grafico.grafico.batch.writer;

import br.tec.ici.grafico.grafico.batch.dto.GraficoResultadoDTO;
import br.tec.ici.grafico.grafico.service.GraficoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
@StepScope
public class GraficoWriter implements ItemWriter<GraficoResultadoDTO> {

  private final GraficoService graficoService;


  @Override
  public void write(Chunk<? extends GraficoResultadoDTO> chunk) throws Exception {
    try {
      chunk.forEach(result ->
          graficoService.salvar(result.getCdGrafico(), result.getJson()));
    } catch (Exception e) {
      log.error(e);
    }

  }
}
