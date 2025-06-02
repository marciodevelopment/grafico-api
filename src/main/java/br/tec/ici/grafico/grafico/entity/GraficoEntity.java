package br.tec.ici.grafico.grafico.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Table(name = "Grafico")
public class GraficoEntity {

  @Getter
  @Id
  @Column(name = "cdgrafico")
  private Integer cdGrafico;

  @Column(name = "nmGrafico")
  private String nmGrafico;

  @Column(name = "vltempoatualizacao")
  private Integer vlTempoAtualizacao;

  @Column(name = "hratualizacao")
  private String hrAtualizacao;

  @Getter
  @Column(name = "sql", columnDefinition = "TEXT")
  private String sql;

  @Getter
  @Column(name = "nmSistema")
  private String nmSistema;

  @Column(name = "dtcache")
  private LocalDateTime dtCache;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "cdCache")
  private CacheEntity cache;


  public String getJson() {
    if (this.cache == null) {
      return null;
    }
    return this.cache.getJson();
  }


  public GraficoEntity atualizarJson(String json) {
    if (this.cache == null) {
      this.cache = new CacheEntity(this, json);
    }
    this.cache.atualizarJson(json);
    this.dtCache = LocalDateTime.now();
    return this;
  }
}
