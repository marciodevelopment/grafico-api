package br.tec.ici.grafico.grafico.schedule;

import br.tec.ici.grafico.grafico.service.AtualizacaoCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class AtualizacaoGraficoScheduler {

  private final AtualizacaoCacheService atualizacaoCacheService;

  @Scheduled(fixedDelay = 5000) // runs every 5 seconds after the last execution finishes
  public void runJob() {
    this.atualizacaoCacheService.atualizarCache();
  }
}