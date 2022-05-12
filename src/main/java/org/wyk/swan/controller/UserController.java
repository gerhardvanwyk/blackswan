package org.wyk.swan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.wyk.swan.model.TaskRepository;
import org.wyk.swan.model.User;
import org.wyk.swan.model.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    public UserController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping(path="/{id}", params = {}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<User> getUser(@PathVariable Long id){

        Optional<User> usOp = userRepository.findById(id);
        if(usOp.isPresent()){
            return ResponseEntity.ok(usOp.get());
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = new ArrayList();
        userRepository.findAll().forEach(users::add);
        return ResponseEntity.ok(users);
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<User> createUser(@RequestBody User user){
        User u = userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping(path = "/",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<User> updateUser(@RequestBody User user){
        if(user.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User u = userRepository.save(user);
        return ResponseEntity.ok(user);
    }

}
