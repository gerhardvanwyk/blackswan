package org.wyk.swan.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    Optional<Task> findTaskByIdAndUserId(Long userId, Long id);

    Iterable<Task> findTaskByUserId(Long userId);


}
