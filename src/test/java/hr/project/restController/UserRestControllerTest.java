package hr.project.restController;

import hr.project.model.User;
import hr.project.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Before
    public void before() throws Exception {
        User user = new User();
        user.setPassword("a");
        userRepository.save(user);
    }

    @Test
    public void exceptionHandlerTest() throws Exception {
        this.mockMvc.perform(get("/user/100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTest() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdTest() throws Exception {
        this.mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void loginTest() throws Exception {
        this.mockMvc.perform(post("/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1," +
                        "\"name\":null," +
                        "\"email\":null," +
                        "\"password\":\"\"," +
                        "\"image\":null," +
                        "\"semester\":null," +
                        "\"role_id\":null," +
                        "\"title_id\":null," +
                        "\"course_id\":null," +
                        "\"statistics\":[]," +
                        "\"game1\":[]," +
                        "\"game2\":[]," +
                        "\"role\":null," +
                        "\"title\":null}"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void postTest() throws Exception {
        this.mockMvc.perform(post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":null," +
                        "\"email\":null," +
                        "\"password\":\"\"," +
                        "\"image\":null," +
                        "\"semester\":null," +
                        "\"role_id\":null," +
                        "\"title_id\":null," +
                        "\"course_id\":null," +
                        "\"statistics\":[]," +
                        "\"game1\":[]," +
                        "\"game2\":[]," +
                        "\"role\":null," +
                        "\"title\":null}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void putTest() throws Exception {
        this.mockMvc.perform(put("/user/1")
                .content("{\"id\":1," +
                        "\"name\":null," +
                        "\"email\":null," +
                        "\"password\":\"a\"," +
                        "\"image\":null," +
                        "\"semester\":null," +
                        "\"role_id\":null," +
                        "\"title_id\":null," +
                        "\"course_id\":null," +
                        "\"statistics\":[]," +
                        "\"game1\":[]," +
                        "\"game2\":[]," +
                        "\"role\":null," +
                        "\"title\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTest() throws Exception {
        this.mockMvc.perform(delete("/user/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
