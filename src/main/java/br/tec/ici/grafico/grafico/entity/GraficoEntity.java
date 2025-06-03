package br.tec.ici.grafico.grafico.entity;

import br.tec.ici.grafico.grafico.entity.util.CalculaProximaExecucao;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.NumericBooleanConverter;

@Entity
@Table(name = "Grafico")
@SQLRestriction("id_Ativo = 1")
@DynamicUpdate
public class GraficoEntity {


  @OneToMany(cascade = CascadeType.ALL, mappedBy = "grafico", fetch = FetchType.LAZY)
  private final Set<ErroExecucaoEntity> erros = new HashSet<>();
  @Getter
  @Id
  @Column(name = "cd_grafico")
  private Integer cdGrafico;
  @Column(name = "grafico")
  private String grafico;
  @Column(name = "tempo_atualizacao_segundos")
  private Integer tempoAtualizacaoSegundos;
  @Column(name = "horas_atualizacao")
  private String horasAtualizacao;
  @Getter
  @Column(name = "sql", columnDefinition = "TEXT")
  private String sql;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_sistema")
  private SistemaEntity sistema;
  @Column(name = "data_cache")
  private LocalDateTime dataCache;

  @Column(name = "id_Atualizando")
  @Convert(converter = NumericBooleanConverter.class)
  private Boolean idAtualizando;
  @Column(name = "data_proxima_execucao")
  private LocalDateTime dataProximaExecucao;
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "cd_Cache")
  private CacheEntity cache;
  @Column(name = "data_inicio_atualizacao")
  private LocalDateTime dataInicioAtualizacao;
  @Column(name = "data_fim_atualizacao")
  private LocalDateTime dataFimAtualizacao;

  public String getJson() {
    if (this.cache == null) {
      return null;
    }
    return this.cache.getJson();
  }

  public String getIdSistema() {
    return this.sistema.getIdSistema();
  }

  public GraficoEntity atualizando() {
    this.idAtualizando = true;
    this.dataInicioAtualizacao = LocalDateTime.now();
    return this;
  }


  public GraficoEntity atualizarJson(String json) {
    if (this.cache == null) {
      this.cache = new CacheEntity(this, json);
    }
    this.cache.atualizarJson(json);
    this.dataCache = LocalDateTime.now();
    this.dataProximaExecucao = CalculaProximaExecucao.of(LocalDateTime.now(),
        this.tempoAtualizacaoSegundos,
        this.horasAtualizacao);
    this.finalizarAtualizacao();
    return this;
  }


  public GraficoEntity finalizarAtualizacao() {
    this.dataFimAtualizacao = LocalDateTime.now();
    this.idAtualizando = false;
    return this;
  }
}
