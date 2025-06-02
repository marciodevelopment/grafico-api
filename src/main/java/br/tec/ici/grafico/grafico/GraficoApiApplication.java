package br.tec.ici.grafico.grafico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GraficoApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(GraficoApiApplication.class, args);
  }

}
