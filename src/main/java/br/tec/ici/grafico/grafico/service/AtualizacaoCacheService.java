package br.tec.ici.grafico.grafico.service;

import br.tec.ici.grafico.grafico.entity.GraficoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AtualizacaoCacheService {

  private final Map<String, JdbcTemplate> jdbcTemplateMap;
  private final GraficoService graficoService;

  @Async
  public void atualizarCache() {
    var graficos = this.graficoService.findAllExecution();
    log.info("Atualizando graficos {} ", graficos.size());
    graficos
        .parallelStream()
        .forEach(this::process);
    log.info("Atualização finalizada {} ", graficos.size());
  }

  public void process(GraficoEntity grafico) {
    try {
      this.graficoService.atualizando(grafico.getCdGrafico());
      JdbcTemplate jdbcTemplate = jdbcTemplateMap.get(grafico.getIdSistema());
      if (jdbcTemplate == null) {
        throw new IllegalArgumentException("No datasource for sistema: " + grafico.getIdSistema());
      }
      var scripts = grafico.getSql().split(";");
      for (int i = 0; i < scripts.length - 1; i++) {
        jdbcTemplate.execute(scripts[i].replace(";", "").trim());
      }
      var objectMapper = new ObjectMapper();
      List<Map<String, Object>> result = jdbcTemplate.queryForList(
          scripts[scripts.length - 1].replace(";", ""));
      String json = objectMapper
          .writeValueAsString(result);
      this.graficoService.salvar(grafico.getCdGrafico(), json);
    } catch (Exception e) {
      graficoService.salvarErro(grafico.getCdGrafico(), e);
      log.error("Erro ao atualizar cache. Sql: {}. Sistema: {}", grafico.getSql(),
          grafico.getIdSistema());
    }
  }
}
