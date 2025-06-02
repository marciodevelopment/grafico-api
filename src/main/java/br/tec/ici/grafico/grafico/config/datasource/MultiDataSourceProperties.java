package br.tec.ici.grafico.grafico.config.datasource;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "external")
public class MultiDataSourceProperties {

  private Map<String, DataSourceProperties> datasources = new HashMap<>();

  public Map<String, DataSourceProperties> getDatasources() {
    return datasources;
  }

  public void setDatasources(Map<String, DataSourceProperties> datasources) {
    this.datasources = datasources;
  }

  @Data
  public static class DataSourceProperties {

    private String url;
    private String username;
    private String password;
    private String driverClassName;

    // Getters and Setters
  }
}
