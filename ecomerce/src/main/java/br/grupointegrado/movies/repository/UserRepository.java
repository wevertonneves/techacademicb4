package br.grupointegrado.movies.repository;

import br.grupointegrado.movies.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Método para buscar usuário pelo email
    Optional<User> findByEmail(String email);
}
