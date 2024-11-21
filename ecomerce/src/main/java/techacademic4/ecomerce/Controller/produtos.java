package techacademic4.ecomerce.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produtos")
public class produtos {

    @GetMapping
    public ResponseEntity<String> helloWord(){
        return ResponseEntity.ok("hello Woddddddrddddddddddd");
    }
}
