package br.grupointegrado.movies.controller;

import br.grupointegrado.movies.dto.CategoryRequestDTO;
import br.grupointegrado.movies.model.Category;
import br.grupointegrado.movies.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000") // Permite que o frontend na porta 3000 acesse o backend
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = this.repository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não foi encontrada"));
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody CategoryRequestDTO dto) {
        if (dto.nome().isEmpty()) {
            return ResponseEntity.status(428).build();
        }

        Category category = new Category();
        category.setNome(dto.nome());

        this.repository.save(category);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Category category = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não foi encontrada"));

        this.repository.delete(category);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody CategoryRequestDTO dto) {
        if (dto.nome().isEmpty()) {
            return ResponseEntity.status(428).build();
        }

        Category category = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não foi encontrada"));

        category.setNome(dto.nome());

        this.repository.save(category);
        return ResponseEntity.ok(category);
    }
}