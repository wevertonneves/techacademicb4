package br.grupointegrado.movies.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CarrinhoProdutoId implements Serializable {

    private Long carrinhoId;
    private Integer produtoId;

    // Getters e setters

    public Long getCarrinhoId() {
        return carrinhoId;
    }

    public void setCarrinhoId(Long carrinhoId) {
        this.carrinhoId = carrinhoId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    // Equals and HashCode (certifique-se de implementá-los adequadamente)
}
