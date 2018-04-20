package hr.project.restController;

import hr.project.model.Title;
import hr.project.repository.TitleRepository;
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
public class TitleRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TitleRepository titleRepository;

    @Before
    public void before() throws Exception {
        Title title = new Title();
        titleRepository.save(title);
    }

    @Test
    public void exceptionHandlerTest() throws Exception {
        this.mockMvc.perform(get("/title/100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTest() throws Exception {
        this.mockMvc.perform(get("/title"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdTest() throws Exception {
        this.mockMvc.perform(get("/title/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void postTest() throws Exception {
        this.mockMvc.perform(post("/title")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":null," +
                        "\"points\":null}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void putTest() throws Exception {
        this.mockMvc.perform(put("/title/1")
                .content("{\"id\":1," +
                        "\"name\":null," +
                        "\"points\":null}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTest() throws Exception {
        this.mockMvc.perform(delete("/title/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
