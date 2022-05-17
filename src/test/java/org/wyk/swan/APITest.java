package org.wyk.swan;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.wyk.swan.model.Task;
import org.wyk.swan.model.User;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class APITest {

    private static final Logger logger = LoggerFactory.getLogger(APITest.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void testUsers(@Autowired MockMvc mvc) throws Exception {

        //
        try{
            Thread.sleep(5000);
        }catch(Exception e){
            //
        }

        User user = new User();
        user.setUsername("user-"+ RandomUtils.nextLong());

        MvcResult res = mvc.perform(post("/user/")
                        .content(mapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String loc = res.getResponse().getHeader("Location");
        Assertions.assertNotNull(loc);


        res = mvc.perform(put("/user/")
                        .content(mapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        User user1 = new User(1l);
        user1.setFirstName("NewName");
        user1.setLastName("NewLastName");

        res = mvc.perform(put("/user/")
                        .content(mapper.writeValueAsBytes(user1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        mvc.perform(get("/user/" + user1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"username\":\"user_1\",\"error\":null," +
                        "\"first_name\":\"NewName\",\"last_name\":\"NewLastName\"}"));
    }

    @Test
    void testTasks(@Autowired MockMvc mvc) throws Exception {

        //Save a User
        User user = new User();
        user.setUsername("user-"+ RandomUtils.nextLong());

        MvcResult res = mvc.perform(post("/user/")
                        .content(mapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String loc = res.getResponse().getHeader("Location");
        Assertions.assertNotNull(loc);
        User userRet =  mapper.readValue(res.getResponse().getContentAsString(), User.class);
        Long userId = userRet.getId();

        //Add Tasks
        String task1 = "Task1";
        String task1Dec = "Description Task 1";
        addTask(mvc, userId, task1, task1Dec);

        String task2 = "Task2";
        String task2Dec = "Description Task 2";
        addTask(mvc, userId, task2, task2Dec);

        String task3 = "Task3";
        String task3Dec = "Description Task 3";
        addTask(mvc, userId, task3, task3Dec);

        res = mvc.perform(get("/user/" + userId + "/task" )
                        .content(mapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String cont = res.getResponse().getContentAsString();
        Assertions.assertEquals(3, StringUtils.countMatches(cont, userId.toString()));

    }

    private void addTask(MockMvc mvc, Long user_id,  String name, String description) throws Exception {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);


        MvcResult res = mvc.perform(post("/user/" + user_id + "/task")
                        .content(mapper.writeValueAsBytes(task))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }


}
