package org.wyk.swan;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.wyk.swan.controller.UserController;
import org.wyk.swan.model.User;
import org.wyk.swan.model.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mvc;

    @MockBean
    private  UserRepository repository;

    @Test
    public void create() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes( request ));

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");

        User user2 = new  User(14l);
        user2.setFirstName(user1.getFirstName());
        user2.setLastName(user1.getLastName());
        when(repository.save(any(User.class))).thenReturn(user2);


        MvcResult res = mvc.perform(post("/user/")
                        .content(mapper.writeValueAsBytes(user1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Assertions.assertEquals("", res.getResponse().getContentAsString());
        String loc = res.getResponse().getHeader("Location");
        Assertions.assertEquals("http://localhost/user/14", loc);
    }

    @Test
    public void update() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes( request ));

        User user1 = new User();
        user1.setUsername("user1");
        user1.setFirstName("John");
        user1.setLastName("Doe");

        User user2 = new  User(14l);
        user2.setUsername("user1");
        user2.setFirstName(user1.getFirstName());
        user2.setLastName(user1.getLastName());
        when(repository.save(any(User.class))).thenReturn(user2);


        MvcResult res = mvc.perform(put("/user/")
                        .content(mapper.writeValueAsBytes(user2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals("{\"id\":14,\"username\":\"user1\",\"error\":null,\"new\":false," +
                "\"first_name\":\"John\",\"last_name\":\"Doe\"}", res.getResponse().getContentAsString());
    }
}
