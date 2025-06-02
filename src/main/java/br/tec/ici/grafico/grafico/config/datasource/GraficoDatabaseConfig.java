package br.tec.ici.grafico.grafico.config.datasource;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "br.tec.ici.grafico.grafico.repository",entityManagerFactoryRef = "graficoEntityManagerFactory",transactionManagerRef = "graficoTransactionManager")
public class GraficoDatabaseConfig {

  @Primary
  @Bean(name = "graficoDataSource")
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource graficoDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean(name = "graficoEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean graficoEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("graficoDataSource") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages("br.tec.ici.grafico.grafico.entity")
        .persistenceUnit("grafico")
        .build();
  }

  @Primary
  @Bean(name = "graficoTransactionManager")
  public PlatformTransactionManager graficoTransactionManager(
      @Qualifier("graficoEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
