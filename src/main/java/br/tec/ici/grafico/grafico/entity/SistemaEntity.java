package br.tec.ici.grafico.grafico.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "sistema")
public class SistemaEntity {

  @Id
  @Column(name = "id_sistema")
  private String idSistema;

  @Column(name = "nome_sistema", nullable = false)
  private String nome_sistema;

  @Column(name = "url", nullable = false)
  private String url;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "driver_class_name", nullable = false)
  private String driverClassName;

  @Column(name = "jdbc_url", nullable = false)
  private String jdbcUrl;

}
