package br.tec.ici.grafico.grafico.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;


@Entity
@Table(name = "erro_execucao")
public class ErroExecucaoEntity {

  @Id
  @Column(name = "cd_erro_execucao")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer cdErroExecucao;

  @Getter
  @Column(name = "stack_trace")
  private String stackTrace;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cd_Grafico")
  private GraficoEntity grafico;

  private ErroExecucaoEntity() {
  }

  public ErroExecucaoEntity(GraficoEntity grafico, String stackTrace) {
    this.stackTrace = stackTrace;
    this.grafico = grafico;
  }


}