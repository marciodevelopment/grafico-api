package br.tec.ici.grafico.grafico.web.rest;


import br.tec.ici.grafico.grafico.service.GraficoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("graficos")
@RequiredArgsConstructor
public class GraficosController {

  private final GraficoService graficoService;
  private final Map<String, JdbcTemplate> jdbcTemplateMap;

  @GetMapping(path = "/{nmGrafico}")
  public String getGrafico(@PathVariable String nmGrafico) throws JsonProcessingException {
    log.debug("Get grafico {}", nmGrafico);

    this.jdbcTemplateMap.forEach((k, v) -> log.info(k));

    JdbcTemplate jdbcTemplate = jdbcTemplateMap.get("samu");
    List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from login");
    String json = new ObjectMapper().writeValueAsString(result);
    log.info("json {}", json);
    return this.graficoService.findJsonByGrafico(nmGrafico);

  }


}
