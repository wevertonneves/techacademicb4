package techacademic4.ecomerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techacademic4.ecomerce.Domain.categoria;
import techacademic4.ecomerce.repository.categoriaRepository;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class categoriaController {

    @Autowired
    private categoriaRepository repository;

    @GetMapping
    public ResponseEntity<List<categoria>> findAll() {
        List<categoria> categorias = repository.findAll();
        return ResponseEntity.ok(categorias);
    }
}
