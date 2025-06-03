package br.tec.ici.grafico.grafico.service;

import br.tec.ici.grafico.grafico.entity.ErroExecucaoEntity;
import br.tec.ici.grafico.grafico.repository.ErroExecucaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ErroExecucaoService {

  private final ErroExecucaoRepository erroExecucaoRepository;

  public void save(ErroExecucaoEntity erroExecucao) {
    this.erroExecucaoRepository.save(erroExecucao);
  }
}
