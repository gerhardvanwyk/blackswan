package org.wyk.swan;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.wyk.swan.model.User;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
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
        user.setUsername("user"+ UUID.randomUUID().toString());

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
                .andExpect(content().string("{\"id\":1,\"username\":\"user_1\",\"error\":null,\"new\":false," +
                        "\"first_name\":\"NewName\",\"last_name\":\"NewLastName\"}"));
    }

}
