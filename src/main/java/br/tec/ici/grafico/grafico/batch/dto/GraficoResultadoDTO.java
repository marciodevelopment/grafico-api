package br.tec.ici.grafico.grafico.batch.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GraficoResultadoDTO {

  private final Integer cdGrafico;
  private final String json;
}
