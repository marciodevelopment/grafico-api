package br.tec.ici.grafico.grafico.config.datasource;

import br.tec.ici.grafico.grafico.entity.SistemaEntity;
import br.tec.ici.grafico.grafico.service.SistemaService;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
@Configuration
public class DynamicJdbcTemplateConfig {

  private final SistemaService sistemaService;

  @Bean
  public Map<String, JdbcTemplate> jdbcTemplateMap() {

    return sistemaService.findAll().stream()
        .collect(Collectors.toMap(
            SistemaEntity::getIdSistema,
            config -> {
              DataSource dataSource = DataSourceBuilder.create()
                  .url(config.getUrl())
                  .username(config.getUsername())
                  .password(config.getPassword())
                  .driverClassName(config.getDriverClassName())
                  .build();
              return new JdbcTemplate(dataSource);
            }
        ));
  }
}
