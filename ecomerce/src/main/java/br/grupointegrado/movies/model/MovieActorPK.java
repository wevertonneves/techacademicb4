package br.grupointegrado.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

// indica que essa classe pode ser INCORPORÁVEL
@Embeddable
public class MovieActorPK {

    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "actor_id")
    private Integer actorId;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieActorPK that = (MovieActorPK) o;
        return Objects.equals(movieId, that.movieId) && Objects.equals(actorId, that.actorId);
    }

    public int hashCode() {
        return Objects.hash(movieId, actorId);
    }
}
