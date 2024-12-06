package br.grupointegrado.movies.dto;

import java.math.BigDecimal;

public record MovieRequestDTO(String nome, String resumo, String imageUrl, BigDecimal preco) {
}
