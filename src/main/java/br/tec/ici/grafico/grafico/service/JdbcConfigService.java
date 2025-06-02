package br.tec.ici.grafico.grafico.service;

import br.tec.ici.grafico.grafico.entity.JdbcConfigEntity;
import br.tec.ici.grafico.grafico.repository.JdbcConfigRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JdbcConfigService {

  private final JdbcConfigRepository jdbcConfigRepository;

  public List<JdbcConfigEntity> findAll() {
    return this.jdbcConfigRepository.findAll();
  }
}
