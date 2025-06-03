package br.tec.ici.grafico.grafico.repository;


import br.tec.ici.grafico.grafico.entity.ErroExecucaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErroExecucaoRepository extends JpaRepository<ErroExecucaoEntity, String> {

}
