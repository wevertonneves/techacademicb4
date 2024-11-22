package techacademic4.ecomerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techacademic4.ecomerce.DTO.produtosRequestDTO;
import techacademic4.ecomerce.Domain.produtos;
import techacademic4.ecomerce.repository.produtosRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class produtosController {

    @Autowired
    private produtosRepository repository;

    @GetMapping
    public ResponseEntity<List<produtos>> findAll() {
        List<produtos> produtos = this.repository.findAll();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<produtos> findbyid(@PathVariable Long id){
        Optional<produtos> produtos = this.repository.findById(id);

        if (produtos.isPresent()){
            return ResponseEntity.ok(produtos.get());
        }
return ResponseEntity.noContent().build();
    }
@PostMapping
    public  ResponseEntity<produtos> create(@RequestBody  produtosRequestDTO produtosDTO)
    {
     produtos produtos = new produtos();
     produtos.setNome(produtosDTO.nome());
     produtos.setCategoria(produtosDTO.categoria());

     this.repository.save(produtos);
     return  ResponseEntity.ok(produtos);
    }


}
