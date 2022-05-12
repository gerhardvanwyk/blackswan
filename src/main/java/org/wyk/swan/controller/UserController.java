package org.wyk.swan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.wyk.swan.model.User;
import org.wyk.swan.model.UserRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<User> get(@PathVariable Long id){

        Optional<User> usOp = userRepository.findById(id);
        if(usOp.isPresent()){
            return ResponseEntity.ok(usOp.get());
        }else {
            User usr = new User();
            usr.setMessage("User " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usr);
        }
    }

    @GetMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<User>> getAll(){
        List<User> users = new ArrayList();
        userRepository.findAll().forEach(users::add);
        return ResponseEntity.ok(users);
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<User> create(@RequestBody User user){
        User u = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(u.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<User> update(@RequestBody User user){
        if(user.getId() == null){
            user.setMessage("User  Id cannot  be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }
        User u = userRepository.save(user);
        return ResponseEntity.ok(u);
    }

}