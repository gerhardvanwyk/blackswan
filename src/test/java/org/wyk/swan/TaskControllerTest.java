package org.wyk.swan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wyk.swan.controller.TaskController;
import org.wyk.swan.model.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @InjectMocks
    TaskController  taskController;

    @Mock
    TaskRepository repository;

}
