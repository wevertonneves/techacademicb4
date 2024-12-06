package br.grupointegrado.movies.repository;

import br.grupointegrado.movies.model.CarrinhoProduto;
import br.grupointegrado.movies.model.CarrinhoProdutoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoProdutoRepository extends JpaRepository<CarrinhoProduto, CarrinhoProdutoId> {
    // Métodos personalizados, se necessário
}
