package org.wyk.swan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.wyk.swan.controller.TaskController;
import org.wyk.swan.controller.UserController;
import org.wyk.swan.model.TaskRepository;
import org.wyk.swan.model.User;
import org.wyk.swan.model.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    UserRepository repository;

    @Test
    public void create(){

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes( request ));

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");

        User user2 = new  User(14l);
        user2.setFirstName(user1.getFirstName());
        user2.setLastName(user1.getLastName());
        when(repository.save(any(User.class))).thenReturn(user2);


        mvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(alex.getName())));

    }
}
