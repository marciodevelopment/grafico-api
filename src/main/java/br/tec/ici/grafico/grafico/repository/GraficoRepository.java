package br.tec.ici.grafico.grafico.repository;


import br.tec.ici.grafico.grafico.entity.GraficoEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraficoRepository extends JpaRepository<GraficoEntity, Integer> {

  @EntityGraph(attributePaths = {"cache"})
  Optional<GraficoEntity> findByNmGrafico(String nmGrafico);
}
