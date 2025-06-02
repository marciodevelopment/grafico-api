package br.tec.ici.grafico.grafico.batch.schedule;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GraficoJobScheduler {

  private final JobLauncher jobLauncher;
  private final Job envioFilaJob;

  public GraficoJobScheduler(JobLauncher jobLauncher,
      @Qualifier("atualizacaoGraficoJob") Job envioFilaJob) {
    this.jobLauncher = jobLauncher;
    this.envioFilaJob = envioFilaJob;
  }

  @Scheduled(fixedDelay = 5000) // runs every 5 seconds after the last execution finishes
  public void runJob() {
    try {
      JobParameters jobParameters = new JobParametersBuilder()
          .addLong("timestamp", System.currentTimeMillis()) // unique job params to allow rerun
          .toJobParameters();

      jobLauncher.run(envioFilaJob, jobParameters);
    } catch (Exception e) {
      e.printStackTrace(); // or use your logging system
    }
  }
}