package br.grupointegrado.movies.dto;

public record UserDTO(
        Long id,
        String name,
        String email,
        String phone
) {}