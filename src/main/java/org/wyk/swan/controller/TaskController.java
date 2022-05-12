package org.wyk.swan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.wyk.swan.model.Task;
import org.wyk.swan.model.TaskRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping(path = "/{user_id}/task/{id}")
    public ResponseEntity<Task> get(@PathVariable Long user_id, @PathVariable Long id){
       final Optional<Task> opTsk =  this.taskRepository.findTaskByIdAndUserId(user_id, id);
       if(opTsk.isPresent()){
           return ResponseEntity.ok(opTsk.get());
       }else {
           Task tsk = new Task();
           tsk.setError("Could not find task " + id + " for user " + user_id);
           logger.warn("Could not find task {} for user {}", id, user_id);
           return new ResponseEntity<>(tsk, HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping(path = "/{user_id}/task")
    public ResponseEntity<List<Task>> getAll(@PathVariable Long user_id){
        final List<Task> tasks = new ArrayList<>();
        taskRepository.findTaskByUserId(user_id).forEach(tasks::add);
        logger.debug("Found {} task for user {}", tasks.size(), user_id);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping(path = "/{user_id}/task")
    public ResponseEntity<Task> create(@PathVariable Long user_id, @RequestBody Task task){
        task.setUserId(user_id);
        final Task tks  = this.taskRepository.save(task);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{user_id}/task/{task_id}")
                .buildAndExpand(user_id, tks.getId())
                .toUri();
        logger.debug("Task {} created for user {}", tks.getId(), user_id);
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{user_id}/task")
    public ResponseEntity<Task> update(@RequestBody Task task){
        if(task.getId() == null){
            Task tsk = new Task();
            tsk.setError("User  Id cannot  be null");
            logger.warn("Cannot update a new Task");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tsk);
        }
        final Task result = this.taskRepository.save(task);
        logger.debug("Task {} updated", task.getId());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/{user_id}/task/{id}")
    public ResponseEntity<Task> delete(@PathVariable Long user_id, @PathVariable Long id){
        Optional<Task> tsk = this.taskRepository.findTaskByIdAndUserId(user_id, id);
        if(tsk.isPresent()) {
            this.taskRepository.delete(tsk.get());
            tsk.get().setError("Task deleted");
            logger.debug("Task {} deleted for user {}", id, user_id);
            return ResponseEntity.ok(tsk.get());
        }else {
            Task del = new Task(id);
            del.setError("Could not find task to delete");
            del.setUserId(user_id);
            return new ResponseEntity<>(del, HttpStatus.NOT_FOUND  );
        }
    }
}
