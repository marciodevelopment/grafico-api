package br.tec.ici.grafico.grafico.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
@Table(name = "cache")
public class CacheEntity {

  @Getter
  @Column(name = "json")
  @JdbcTypeCode(SqlTypes.JSON)
  private String json;
  @OneToOne(fetch = FetchType.LAZY, mappedBy = "cache")
  private GraficoEntity grafico;
  @Id
  @Column(name = "cd_Cache")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer cdCache;

  protected CacheEntity() {
  }

  public CacheEntity(GraficoEntity grafico, String json) {
    this.json = json;
    this.grafico = grafico;
  }

  public void atualizarJson(String json) {
    this.json = json;
  }
}