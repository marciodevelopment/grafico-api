package br.tec.ici.grafico.grafico.service;

import br.tec.ici.grafico.grafico.entity.SistemaEntity;
import br.tec.ici.grafico.grafico.repository.SistemaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SistemaService {

  private final SistemaRepository sistemaRepository;

  public List<SistemaEntity> findAll() {
    return this.sistemaRepository.findAll();
  }
}
