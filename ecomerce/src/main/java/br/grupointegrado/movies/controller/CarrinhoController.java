package br.grupointegrado.movies.controller;

import br.grupointegrado.movies.dto.CarrinhoDTO;
import br.grupointegrado.movies.model.Carrinho;
import br.grupointegrado.movies.model.Products;
import br.grupointegrado.movies.repository.CarrinhoRepository;
import br.grupointegrado.movies.repository.ProductsRepository;
import br.grupointegrado.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductsRepository productsRepository;

    // Criar um novo carrinho para o usuário com produtos
    @PostMapping
    public ResponseEntity<CarrinhoDTO> createCarrinho(@RequestBody CarrinhoDTO carrinhoDTO) {
        Carrinho carrinho = new Carrinho();
        carrinho.setUser(userRepository.findById(carrinhoDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado.")));

        List<Products> products = productsRepository.findAllById(carrinhoDTO.productIds());
        carrinho.setProducts(products);

        carrinho = carrinhoRepository.save(carrinho);
        return ResponseEntity.ok(new CarrinhoDTO(
                carrinho.getId(),
                carrinho.getUser().getId(),
                carrinho.getProducts().stream().map(Products::getId).collect(Collectors.toList())
        ));
    }

    // Visualizar carrinho por ID
    @GetMapping("/{id}")
    public ResponseEntity<CarrinhoDTO> getCarrinhoById(@PathVariable Long id) {
        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho não encontrado."));

        return ResponseEntity.ok(new CarrinhoDTO(
                carrinho.getId(),
                carrinho.getUser().getId(),
                carrinho.getProducts().stream().map(Products::getId).collect(Collectors.toList())
        ));
    }

    // Adicionar produtos a um carrinho existente
    @PutMapping("/{id}/add-products")
    public ResponseEntity<CarrinhoDTO> addProductsToCarrinho(
            @PathVariable Long id,
            @RequestBody List<Integer> productIds) {

        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho não encontrado."));

        List<Products> products = productsRepository.findAllById(productIds);
        carrinho.getProducts().addAll(products);

        carrinho = carrinhoRepository.save(carrinho);
        return ResponseEntity.ok(new CarrinhoDTO(
                carrinho.getId(),
                carrinho.getUser().getId(),
                carrinho.getProducts().stream().map(Products::getId).collect(Collectors.toList())
        ));
    }

    // Excluir carrinho
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarrinho(@PathVariable Long id) {
        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho não encontrado."));

        carrinhoRepository.delete(carrinho);
        return ResponseEntity.ok("Carrinho com ID " + id + " foi excluído com sucesso.");
    }

    // Excluir um produto específico do carrinho
    @PutMapping("/{id}/remove-product")
    public ResponseEntity<CarrinhoDTO> removeProductFromCarrinho(
            @PathVariable Long id,
            @RequestBody Integer productId) {

        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho não encontrado."));

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        if (carrinho.getProducts().contains(product)) {
            carrinho.getProducts().remove(product);
            carrinho = carrinhoRepository.save(carrinho);
        } else {
            return ResponseEntity.status(404).body(null); // Produto não encontrado no carrinho
        }

        return ResponseEntity.ok(new CarrinhoDTO(
                carrinho.getId(),
                carrinho.getUser().getId(),
                carrinho.getProducts().stream().map(Products::getId).collect(Collectors.toList())
        ));
    }
}
