package br.tec.ici.grafico.grafico.config.datasource;

import br.tec.ici.grafico.grafico.entity.SistemaEntity;
import br.tec.ici.grafico.grafico.service.SistemaService;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
            this::createPooledDataSource)
        );
  }

  private JdbcTemplate createPooledDataSource(SistemaEntity config) {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(config.getUrl());
    dataSource.setUsername(config.getUsername());
    dataSource.setPassword(config.getPassword());
    dataSource.setDriverClassName(config.getDriverClassName());
    dataSource.setMaximumPoolSize(3);
    dataSource.setPoolName("Pool-" + config.getIdSistema());
    return new JdbcTemplate(dataSource);
  }
}
