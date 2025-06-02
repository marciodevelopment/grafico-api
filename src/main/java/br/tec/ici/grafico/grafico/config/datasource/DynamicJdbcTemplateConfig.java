package br.tec.ici.grafico.grafico.config.datasource;

import br.tec.ici.grafico.grafico.entity.JdbcConfigEntity;
import br.tec.ici.grafico.grafico.service.JdbcConfigService;
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

  private final JdbcConfigService jdbcConfigService;

  @Bean
  public Map<String, JdbcTemplate> jdbcTemplateMap() {

    return jdbcConfigService.findAll().stream()
        .collect(Collectors.toMap(
            JdbcConfigEntity::getNmSistema,
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
