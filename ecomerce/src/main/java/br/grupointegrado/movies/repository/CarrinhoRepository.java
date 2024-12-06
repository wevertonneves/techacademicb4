package br.grupointegrado.movies.repository;

import br.grupointegrado.movies.model.Carrinho;
import br.grupointegrado.movies.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    // Adiciona o método para encontrar o carrinho pelo ID do usuário
    Optional<Carrinho> findByUserId(Long userId);
}
