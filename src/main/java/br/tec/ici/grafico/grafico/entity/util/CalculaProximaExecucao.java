package br.tec.ici.grafico.grafico.entity.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

public class CalculaProximaExecucao {

  public static LocalDateTime of(LocalDateTime dataAtual, Integer tempoAtualizacaoSegundos,
      String horasAtualizacao) {
    if (tempoAtualizacaoSegundos != null) {
      return dataAtual.plusSeconds(tempoAtualizacaoSegundos);
    }
    var horaAtual = dataAtual.getHour();
    var horas = Stream.of(horasAtualizacao.split(";"))
        .map(hora -> Integer.parseInt(hora.trim())).toList();

    for (int hora : horas) {
      if (hora > horaAtual) {
        return dataAtual.toLocalDate().atTime(LocalTime.of(hora, 0));
      }
    }

    return dataAtual.toLocalDate()
        .plusDays(1)
        .atTime(LocalTime.of(horas.getFirst(), 0));
  }
}
