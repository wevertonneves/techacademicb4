package br.grupointegrado.movies.model;

import jakarta.persistence.*;

@Entity
@Table(name = "carrinho_produtos")
public class CarrinhoProduto {

    @EmbeddedId
    private CarrinhoProdutoId id; // Usando a chave composta

    @ManyToOne
    @MapsId("carrinhoId") // Mapeia o campo "carrinhoId" da chave composta
    @JoinColumn(name = "carrinho_id") // Especifica o nome correto da coluna
    private Carrinho carrinho;

    @ManyToOne
    @MapsId("produtoId") // Mapeia o campo "produtoId" da chave composta
    @JoinColumn(name = "produto_id") // Especifica o nome correto da coluna
    private Products produto;

    @Column(name = "quantidade")
    private Integer quantidade;

    // Getters e setters
    public CarrinhoProdutoId getId() {
        return id;
    }

    public void setId(CarrinhoProdutoId id) {
        this.id = id;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public Products getProduto() {
        return produto;
    }

    public void setProduto(Products produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
