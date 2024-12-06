package br.grupointegrado.movies.dto;

import java.math.BigDecimal;

public record ProductsRequestDTO(
        String nome,
        String descricao,
        String imageUrl,
        BigDecimal preco,
        Integer categoryId // Adicionei o campo categoryId aqui
) {
    // Não há necessidade de getters, o record já gera automaticamente os métodos para acessar os campos
}
