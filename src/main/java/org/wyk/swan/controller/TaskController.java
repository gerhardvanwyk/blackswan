package org.wyk.swan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wyk.swan.model.Task;
import org.wyk.swan.model.TaskRepository;
import org.wyk.swan.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class TaskController {


    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping(path = "/{user_id}/task/{task_id}")
    public ResponseEntity<Task> get(Long userId, Long id){
       final Optional<Task> opTsk =  this.taskRepository.findTaskByIdAndUserId(userId, id);
       if(opTsk.isPresent()){
           return ResponseEntity.ok(opTsk.get());
       }else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping(path = "/{user_id}/task")
    public ResponseEntity<List<Task>> getAll(Long userId){
        final List<Task> tasks = new ArrayList();
        taskRepository.findTaskByUserId(userId).forEach(tasks::add);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping(path = "/{user_id}/task")
    public ResponseEntity<Task> create(@RequestBody Task task){
        final Task tks  = this.taskRepository.save(task);
        return ResponseEntity.ok(tks);
    }

    @PutMapping(path = "/{user_id}/task")
    public ResponseEntity<Task> update(@RequestBody Task task){
        final Task tks = this.taskRepository.save(task);
        return ResponseEntity.ok(tks);
    }

    @DeleteMapping(path = "/{user_id}/{task/{id}")
    public ResponseEntity<String> delete(Long userId, Long id){
        Optional<Task> tsk = this.taskRepository.findTaskByIdAndUserId(userId, id);
        if(tsk.isPresent()) {
            this.taskRepository.delete(tsk.get());
            return ResponseEntity.ok("{\"msg\":\"Task delete " + id + " \"}");
        }else {
            return new ResponseEntity("{\"msg\":\"Task " + id + " not found for user " + userId + "\"", HttpStatus.NOT_FOUND  );
        }
    }
}
