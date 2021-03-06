package org.wyk.swan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.wyk.swan.controller.TaskController;
import org.wyk.swan.model.Task;
import org.wyk.swan.model.TaskRepository;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(TaskController.class)
@AutoConfigureDataJpa
public class TaskControllerTest {

    private ObjectMapper mapper = new ObjectMapper();
    {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskRepository repository;

    @Test
    public void create() throws Exception {

        Task tk1 = new Task();
        tk1.setDescription("New task");
        tk1.setName("new");
        tk1.setDateTime(LocalDateTime.now());
        tk1.setUserId(14l);

        Task mock = new  Task(123l);
        mock.setDescription("New task");
        mock.setName("new");
        mock.setDateTime(tk1.getDateTime());
        mock.setUserId(14l);
        when(repository.save(any(Task.class))).thenReturn(mock);


        MvcResult res = mvc.perform(post("/user/14/task/")
                        .content(mapper.writeValueAsBytes(tk1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Assertions.assertEquals("", res.getResponse().getContentAsString());
        String loc = res.getResponse().getHeader("Location");
        Assertions.assertEquals("http://localhost/user/14/task/123", loc);
    }

    @Test
    public void update() throws Exception {
        Task tk1 = new Task();
        tk1.setDescription("New task");
        tk1.setName("new");
        tk1.setDateTime(LocalDateTime.now());
        tk1.setUserId(14l);

        Task mock = new  Task(123l);
        mock.setDescription("New task");
        mock.setName("new");
        mock.setDateTime(tk1.getDateTime());
        mock.setUserId(14l);
        when(repository.save(any(Task.class))).thenReturn(mock);


        MvcResult res = mvc.perform(post("/user/14/task/123")
                        .content(mapper.writeValueAsBytes(tk1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Assertions.assertEquals("", res.getResponse().getContentAsString());
    }


    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper map = new ObjectMapper();

        Task task = map.readValue("{\"name\":\"test name\"}", Task.class);

    }


}
