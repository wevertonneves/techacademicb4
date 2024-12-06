package br.grupointegrado.movies.dto;

import java.math.BigDecimal;

public record ProductsRequestDTO(String nome, String descricao, String imageUrl, BigDecimal preco) {
}
