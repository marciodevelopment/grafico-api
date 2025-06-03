package br.tec.ici.grafico.grafico.repository;


import br.tec.ici.grafico.grafico.entity.SistemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaRepository extends JpaRepository<SistemaEntity, String> {

}
