package br.grupointegrado.movies.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "preco")
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category; // Relacionamento com a entidade Category

    // Getters e Setters

    public Integer getId() {
        return id; // Método getter para o campo 'id'
    }

    public void setId(Integer id) {
        this.id = id; // Método setter para o campo 'id'
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    // Alterei para 'setDescricao', já que o campo é a descrição do produto
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Category getCategory() {
        return category;
    }

    // O setCategory só é necessário se você for setar explicitamente a categoria do produto
    public void setCategory(Category category) {
        this.category = category;
    }
}
