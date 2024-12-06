package br.grupointegrado.movies.repository;

import br.grupointegrado.movies.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
}
