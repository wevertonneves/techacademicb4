package br.grupointegrado.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "movies_actors")
public class MovieActor { // atuacao

    @EmbeddedId // representa que eu tenho uma chave composta
    // embedded = incorporada
    private MovieActorPK id;

    @ManyToOne
    // representa que essa coluna já foi mapeada dentro da PK
    @MapsId("movieId")
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @JsonIgnoreProperties("actors")
    private Movie movie;

    @ManyToOne
    @MapsId("actorId")
    @JoinColumn(name = "actor_id", referencedColumnName = "id")
    private Actor actor;

    @Column(name = "movie_date")
    private LocalDate movieDate;

    public MovieActorPK getId() {
        return id;
    }

    public void setId(MovieActorPK id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public LocalDate getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(LocalDate movieDate) {
        this.movieDate = movieDate;
    }
}
