package br.grupointegrado.movies.controller;

import br.grupointegrado.movies.dto.CarrinhoDTO;
import br.grupointegrado.movies.dto.ProdutoQuantidadeDTO;
import br.grupointegrado.movies.model.Carrinho;
import br.grupointegrado.movies.model.CarrinhoProduto;
import br.grupointegrado.movies.model.CarrinhoProdutoId;
import br.grupointegrado.movies.model.Products;
import br.grupointegrado.movies.repository.CarrinhoProdutoRepository;
import br.grupointegrado.movies.repository.CarrinhoRepository;
import br.grupointegrado.movies.repository.ProductsRepository;
import br.grupointegrado.movies.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carrinhos")
public class CarrinhoController {

    private final CarrinhoRepository carrinhoRepository;
    private final ProductsRepository productsRepository;
    private final CarrinhoProdutoRepository carrinhoProdutoRepository;
    private final UserRepository userRepository;

    public CarrinhoController(CarrinhoRepository carrinhoRepository,
                              ProductsRepository productsRepository,
                              CarrinhoProdutoRepository carrinhoProdutoRepository,
                              UserRepository userRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.productsRepository = productsRepository;
        this.carrinhoProdutoRepository = carrinhoProdutoRepository;
        this.userRepository = userRepository;
    }

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

    // Adicionar produtos ao carrinho
    @PostMapping("/{carrinhoId}/produtos")
    public ResponseEntity<String> addProductToCarrinho(
            @PathVariable Long carrinhoId,
            @RequestBody ProdutoQuantidadeDTO dto) {

        // Verificar se o carrinho existe
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        // Verificar se o produto existe
        Products produto = productsRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Criar ou atualizar o relacionamento no carrinho
        CarrinhoProdutoId id = new CarrinhoProdutoId();
        id.setCarrinhoId(carrinhoId);
        id.setProdutoId(produto.getId());

        CarrinhoProduto carrinhoProduto = carrinhoProdutoRepository.findById(id)
                .orElse(new CarrinhoProduto());

        carrinhoProduto.setId(id);
        carrinhoProduto.setProduto(produto);
        carrinhoProduto.setQuantidade(
                Optional.ofNullable(carrinhoProduto.getQuantidade()).orElse(0) + dto.quantidade()
        );

        carrinhoProdutoRepository.save(carrinhoProduto);

        return ResponseEntity.ok("Produto adicionado ao carrinho");
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
