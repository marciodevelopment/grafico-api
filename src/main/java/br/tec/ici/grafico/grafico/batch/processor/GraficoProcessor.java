package br.tec.ici.grafico.grafico.batch.processor;

import br.tec.ici.grafico.grafico.batch.dto.GraficoResultadoDTO;
import br.tec.ici.grafico.grafico.entity.GraficoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
@StepScope
public class GraficoProcessor implements ItemProcessor<GraficoEntity, GraficoResultadoDTO> {

  private final Map<String, JdbcTemplate> jdbcTemplateMap;


  @Override
  public GraficoResultadoDTO process(GraficoEntity grafico) throws Exception {
    JdbcTemplate jdbcTemplate = jdbcTemplateMap.get(grafico.getNmSistema());

    if (jdbcTemplate == null) {
      throw new IllegalArgumentException("No datasource for sistema: " + grafico.getNmSistema());
    }
    var objectMapper = new ObjectMapper();
    List<Map<String, Object>> result = jdbcTemplate.queryForList(grafico.getSql());
    String json = objectMapper
        .writeValueAsString(result);
    return new GraficoResultadoDTO(grafico.getCdGrafico(), json);
  }
}