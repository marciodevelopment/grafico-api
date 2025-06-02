package br.tec.ici.grafico.grafico.batch;

import br.tec.ici.grafico.grafico.batch.dto.GraficoResultadoDTO;
import br.tec.ici.grafico.grafico.entity.GraficoEntity;
import java.util.concurrent.Future;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class GraficoBatchConfig {

  private static final int CHUNK_SIZE = 100;

  @Bean("atualizacaoGraficoJob")
  public Job envioFilaJob(JobRepository jobRepository,
      @Qualifier("atualizacaoStep") Step atualizacaoStep) {
    return new JobBuilder("envioFilaJob", jobRepository)
        .incrementer(new RunIdIncrementer())
        .start(atualizacaoStep)
        .build();
  }

  @Bean("atualizacaoStep")
  public Step filaEsperaStep(JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      JpaPagingItemReader<GraficoEntity> graficoReader,
      //ItemProcessor<GraficoEntity, Future<GraficoResultadoDTO>> asyncGraficoProcessor,
      ItemProcessor<GraficoEntity, GraficoResultadoDTO> asyncGraficoProcessor,
      //ItemWriter<Future<GraficoResultadoDTO>> asyncGraficoWriter) {
      ItemWriter<GraficoResultadoDTO> asyncGraficoWriter) {
    return new StepBuilder("atualizacaoStep", jobRepository)

        //.<GraficoEntity, Future<GraficoResultadoDTO>>chunk(CHUNK_SIZE, transactionManager)
        .<GraficoEntity, GraficoResultadoDTO>chunk(CHUNK_SIZE, transactionManager)

        .reader(graficoReader)
        .processor(asyncGraficoProcessor)
        .writer(asyncGraficoWriter)
        .faultTolerant()
        .skipLimit(Integer.MAX_VALUE)
        .skip(Exception.class)
        .build();
  }

  //@Bean
  public ItemProcessor<GraficoEntity, Future<GraficoResultadoDTO>> asyncGraficoProcessor(
      TaskExecutor taskExecutor,
      ItemProcessor<GraficoEntity, GraficoResultadoDTO> graficoProcessor) {
    var asyncItemProcessor = new AsyncItemProcessor<GraficoEntity, GraficoResultadoDTO>();
    asyncItemProcessor.setTaskExecutor(taskExecutor);
    asyncItemProcessor.setDelegate(graficoProcessor);
    return asyncItemProcessor;
  }

  //@Bean
  public ItemWriter<Future<GraficoResultadoDTO>> asyncGraficoWriter(
      ItemWriter<GraficoResultadoDTO> graficoWriter) {
    var asyncWriter = new AsyncItemWriter<GraficoResultadoDTO>();
    asyncWriter.setDelegate(graficoWriter);
    return asyncWriter;
  }


}
