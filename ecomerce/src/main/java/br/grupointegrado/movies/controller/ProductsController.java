package br.grupointegrado.movies.controller;

import br.grupointegrado.movies.dto.ProductsRequestDTO;
import br.grupointegrado.movies.model.Products;
import br.grupointegrado.movies.model.Products;
import br.grupointegrado.movies.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductsController {

    @Autowired
    private ProductsRepository repository;

    @GetMapping
    public ResponseEntity<List<Products>> findAll() {
        List<Products> products = this.repository.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public Products findById(@PathVariable Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não foi encontrado"));
    }

    @PostMapping
    public ResponseEntity<Products> save(@RequestBody ProductsRequestDTO dto) {
        if (dto.nome().isEmpty()) {
            return ResponseEntity.status(428).build();
        }

        Products product = new Products();
        product.setNome(dto.nome());
        product.setResumo(dto.resumo());
        product.setImageUrl(dto.imageUrl());
        product.setPreco(dto.preco());

        this.repository.save(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Products product = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não foi encontrado"));

        this.repository.delete(product);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> update(@PathVariable Integer id, @RequestBody ProductsRequestDTO dto) {
        if (dto.nome().isEmpty()) {
            return ResponseEntity.status(428).build();
        }

        Products product = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não foi encontrado"));

        product.setNome(dto.nome());
        product.setResumo(dto.resumo());
        product.setImageUrl(dto.imageUrl());
        product.setPreco(dto.preco());

        this.repository.save(product);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/{id}/resumo")
    public ResponseEntity<Products> addResumo(@PathVariable Integer id, @RequestBody String resumo) {
        Products product = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não foi encontrado"));

        product.setResumo(resumo);
        this.repository.save(product);
        return ResponseEntity.ok(product);
    }
}
