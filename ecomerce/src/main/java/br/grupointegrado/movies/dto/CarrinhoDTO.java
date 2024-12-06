package br.grupointegrado.movies.dto;

import java.util.List;

public record CarrinhoDTO(
        Long id,
        Long userId,
        List<Integer> productIds
) {}
