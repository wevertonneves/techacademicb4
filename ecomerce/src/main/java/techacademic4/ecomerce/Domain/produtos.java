package techacademic4.ecomerce.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class produtos {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String categoria;

}
