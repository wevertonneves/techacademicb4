package br.grupointegrado.movies.controller;

import br.grupointegrado.movies.model.Actor;
import br.grupointegrado.movies.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private ActorRepository repository;

    @GetMapping
    public ResponseEntity<List<Actor>> achaTodoMundo() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @PostMapping
    public Actor salvaOator(@RequestBody Actor actorRequest) {
        Actor actor = new Actor();
        actor.setName(actorRequest.getName());
        actor.setBirthDate(actorRequest.getBirthDate());

        this.repository.save(actor);
        return actor;
    }

}
