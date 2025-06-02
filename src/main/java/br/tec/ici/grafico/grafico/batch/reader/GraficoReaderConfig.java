package br.tec.ici.grafico.grafico.batch.reader;

import br.tec.ici.grafico.grafico.entity.GraficoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class GraficoReaderConfig {

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  @StepScope
  public JpaPagingItemReader<GraficoEntity> graficoReader() {
    JpaPagingItemReader<GraficoEntity> reader = new JpaPagingItemReader<>();
    reader.setEntityManagerFactory(entityManager.getEntityManagerFactory());
    reader.setQueryString(
        "SELECT g FROM GraficoEntity g ");
    reader.setPageSize(1000);
    reader.setSaveState(false);
    return reader;
  }
}