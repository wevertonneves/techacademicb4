package br.grupointegrado.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 200)
    private String nome;

    // mapeamento bi-direcional do ManyToOne, é o caminho reverso
    // esse lado NÃO É OBRIGATÓRIO
    // o mappedBy representa o nome do atributo NO JAVA
    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties({"category", "actors"})
    private List<Movie> movies;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
