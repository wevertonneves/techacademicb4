package techacademic4.ecomerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techacademic4.ecomerce.Domain.produtos;
import techacademic4.ecomerce.repository.produtosRepository;

import java.util.List;

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
}
