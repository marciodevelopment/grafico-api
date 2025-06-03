package br.tec.ici.grafico.grafico.repository;


import br.tec.ici.grafico.grafico.entity.GraficoEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraficoRepository extends JpaRepository<GraficoEntity, Integer> {

  @EntityGraph(attributePaths = {"cache"})
  Optional<GraficoEntity> findByGrafico(String grafico);

  @EntityGraph(attributePaths = {"sistema"})
  List<GraficoEntity> findByDataProximaExecucaoLessThanAndIdAtualizandoFalse(
      LocalDateTime dataAtual);
}
