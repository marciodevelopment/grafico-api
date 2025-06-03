package br.tec.ici.grafico.grafico.entity.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CalculaProximaExecucaoTest {

  @Test
  void proximaExecucaoEmSegundos() {
    var dataAtual = LocalDateTime.now();
    var intervaloProximoExecucao = 180;
    var proximaExecucao = CalculaProximaExecucao.of(dataAtual, intervaloProximoExecucao, null);
    var dataFinal = dataAtual.plusSeconds(intervaloProximoExecucao);
    assertEquals(proximaExecucao, dataFinal);
  }

  @Test
  void proximaExecucaoCincoHoras() {
    int dayOfMonth = 15;
    var dataAtual = LocalDateTime.of(2024, 1, dayOfMonth, 5, 30);
    var proximaExecucao = CalculaProximaExecucao.of(dataAtual, null, "10");
    assertEquals(dayOfMonth, proximaExecucao.getDayOfMonth());
    assertEquals(10, proximaExecucao.getHour());
  }

  @Test
  void proximaExecucaoProximoDia() {
    int dayOfMonth = 15;
    var dataAtual = LocalDateTime.of(2024, 1, dayOfMonth, 23, 30);
    var proximaExecucao = CalculaProximaExecucao.of(dataAtual, null, "10");
    assertEquals(dayOfMonth + 1, proximaExecucao.getDayOfMonth());
    assertEquals(10, proximaExecucao.getHour());
  }

  @Test
  void proximaExecucaoMesmoDiaVariasHoras() {
    int dayOfMonth = 15;
    var dataAtual = LocalDateTime.of(2024, 1, dayOfMonth, 15, 30);
    var proximaExecucao = CalculaProximaExecucao.of(dataAtual, null, "10; 15; 16");
    assertEquals(dayOfMonth, proximaExecucao.getDayOfMonth());
    assertEquals(16, proximaExecucao.getHour());
  }


  @Test
  void proximaExecucaoProximoDiaVariasHoras() {
    int dayOfMonth = 15;
    var dataAtual = LocalDateTime.of(2024, 1, dayOfMonth, 16, 30);
    var proximaExecucao = CalculaProximaExecucao.of(dataAtual, null, "10; 15; 16");
    assertEquals(dayOfMonth + 1, proximaExecucao.getDayOfMonth());
    assertEquals(10, proximaExecucao.getHour());
  }

}