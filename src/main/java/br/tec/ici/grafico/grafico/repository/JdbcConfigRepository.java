package br.tec.ici.grafico.grafico.repository;


import br.tec.ici.grafico.grafico.entity.JdbcConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JdbcConfigRepository extends JpaRepository<JdbcConfigEntity, Integer> {

}
