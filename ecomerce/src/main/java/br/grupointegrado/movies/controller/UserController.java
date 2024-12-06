package br.grupointegrado.movies.controller;

import br.grupointegrado.movies.dto.UserDTO;
import br.grupointegrado.movies.model.LoginRequest;
import br.grupointegrado.movies.model.User;
import br.grupointegrado.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Endpoint para pegar todos os usuários
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    // Endpoint para pegar um usuário pelo id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone())))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para criar um novo usuário
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPhone(userDTO.phone());
        user = userRepository.save(user);
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone());
    }

    // Endpoint para atualizar um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            user.setName(userDTO.name());
            user.setEmail(userDTO.email());
            user.setPhone(userDTO.phone());
            userRepository.save(user);
            return ResponseEntity.ok(new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone()));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint de login utilizando apenas o email
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest) {
        // Buscar o usuário pelo email
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Retornar os dados do usuário (sem senha)
            return ResponseEntity.ok(new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone()));
        } else {
            return ResponseEntity.status(404).body(null);  // Retorna 404 se o usuário não for encontrado
        }
    }
}
