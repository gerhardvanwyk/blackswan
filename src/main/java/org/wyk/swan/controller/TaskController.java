package org.wyk.swan.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.wyk.swan.model.Task;
import org.wyk.swan.model.TaskRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;
    private final EntityManager entityManager;

    private final ObjectMapper objectMapper;

    public TaskController(TaskRepository taskRepository, EntityManager entityManager, ObjectMapper objectMapper) {
        this.taskRepository = taskRepository;
        this.entityManager = entityManager;
        this.objectMapper = objectMapper;
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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        Root<Task> itemRoot = criteriaQuery.from(Task.class);
        Predicate userIdEquals
                = criteriaBuilder.equal(itemRoot.get("userId"), user_id);
        criteriaQuery.where(userIdEquals);
        tasks.addAll(entityManager.createQuery(criteriaQuery).getResultList());
        logger.debug("Found {} task for user {}", tasks.size(), user_id);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping(path = "/{user_id}/task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Task> create(@PathVariable Long user_id, @RequestBody Task task){
        task.setUserId(user_id);
        final Task tks  = this.taskRepository.save(task);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{task_id}")
                .buildAndExpand(tks.getId())
                .toUri();
        logger.debug("Task {} created for user {}", tks.getId(), user_id);
        return ResponseEntity.created(location).body(tks);
    }

    @PutMapping(path = "/{user_id}/task/{task_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> update(@PathVariable Long user_id, @PathVariable Long task_id, @RequestBody Task req) throws IOException {


        final Optional<Task> tskOp = this.taskRepository.findById(task_id);

        if(tskOp.isEmpty()) {
            return getTaskResponseError("Task  cannot  be found for id ", task_id);
        }

        if(req.getName() != null)
            tskOp.get().setName(req.getName());

        if(req.getDescription() != null)
            tskOp.get().setDescription(req.getDescription());

        if(req.getDateTime() != null)
            tskOp.get().setDateTime(req.getDateTime());

        logger.debug("taskDetails {}", req);

        if(task_id == null){
            Task tsk = new Task();
            tsk.setError("Task Id cannot  be null");
            logger.warn("Cannot update the Task");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tsk);
        }

        final Task result = this.taskRepository.save(tskOp.get());
        logger.debug("Task {} for user {} updated", user_id, tskOp.get().getId());
        return ResponseEntity.ok(result);
    }

    @NotNull
    private ResponseEntity<Task> getTaskResponseError(String x, Long task_id) {
        Task tkError = new Task();
        tkError.setError(x + task_id);
        logger.warn("Cannot update the Task {}", task_id);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tkError);
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
