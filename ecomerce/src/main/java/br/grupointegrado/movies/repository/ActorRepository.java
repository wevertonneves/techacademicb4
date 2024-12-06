package br.grupointegrado.movies.repository;

import br.grupointegrado.movies.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
