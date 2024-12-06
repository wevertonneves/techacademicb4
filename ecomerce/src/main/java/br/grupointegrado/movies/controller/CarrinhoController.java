package br.grupointegrado.movies.controller;

import br.grupointegrado.movies.dto.CarrinhoDTO;
import br.grupointegrado.movies.model.Carrinho;
import br.grupointegrado.movies.model.Products;
import br.grupointegrado.movies.model.User;
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
    @PutMapping("/{userId}/add-products")
    public ResponseEntity<CarrinhoDTO> addProductsToCarrinho(
            @PathVariable Long userId,
            @RequestBody List<Integer> productIds) {

        // Buscar o carrinho do usuário ou criar um novo se não existir
        Carrinho carrinho = carrinhoRepository.findByUserId(userId)
                .orElseGet(() -> {
                    // Criar um novo carrinho se não existir
                    Carrinho newCarrinho = new Carrinho();
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
                    newCarrinho.setUser(user);
                    return carrinhoRepository.save(newCarrinho);  // Salvar novo carrinho
                });

        // Buscar os produtos a serem adicionados
        List<Products> products = productsRepository.findAllById(productIds);
        carrinho.getProducts().addAll(products);

        // Salvar o carrinho com os novos produtos
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
}
