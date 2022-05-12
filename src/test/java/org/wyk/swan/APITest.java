package org.wyk.swan;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.wyk.swan.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class APITest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void testUsers(@Autowired MockMvc mvc) throws Exception {

        User user = new User();
        user.setUsername("userdoe");

       MvcResult res = mvc.perform(post("/api/user/")
                        .content(mapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
               .andExpect(content().string(""))
                .andReturn();
        user.setLastName("Doe");
        user.setFirstName("Joe");

        res = mvc.perform(put("/api/user/")
                        .content(mapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(""))
                .andReturn();
        mvc.perform(get("/api/user/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

}
