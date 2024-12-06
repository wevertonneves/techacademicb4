package br.grupointegrado.movies.controller;

import br.grupointegrado.movies.dto.MovieRequestDTO;
import br.grupointegrado.movies.model.Movie;
import br.grupointegrado.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

    @Autowired
    private MovieRepository repository;

    @GetMapping
    public ResponseEntity<List<Movie>> findAll() {
        List<Movie> movies = this.repository.findAll();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filme não foi encontrado"));
    }

    @PostMapping
    public ResponseEntity<Movie> save(@RequestBody MovieRequestDTO dto) {
        if (dto.nome().isEmpty()) {
            return ResponseEntity.status(428).build();
        }

        Movie movie = new Movie();
        movie.setNome(dto.nome());
        movie.setResumo(dto.resumo());
        movie.setImageUrl(dto.imageUrl());
        movie.setPreco(dto.preco());  // Setar o preço

        this.repository.save(movie);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Movie movie = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filme não foi encontrado"));

        this.repository.delete(movie);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Integer id, @RequestBody MovieRequestDTO dto) {
        if (dto.nome().isEmpty()) {
            return ResponseEntity.status(428).build();
        }

        Movie movie = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filme não foi encontrado"));

        movie.setNome(dto.nome());
        movie.setResumo(dto.resumo());
        movie.setImageUrl(dto.imageUrl());
        movie.setPreco(dto.preco());  // Atualizar o preço

        this.repository.save(movie);
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/{id}/resumo")
    public ResponseEntity<Movie> addResumo(@PathVariable Integer id, @RequestBody String resumo) {
        Movie movie = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filme não foi encontrado"));

        movie.setResumo(resumo);
        this.repository.save(movie);
        return ResponseEntity.ok(movie);
    }
}
