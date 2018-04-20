package hr.project.restController;

import hr.project.model.Question;
import hr.project.repository.QuestionRepository;
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
public class QuestionRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    QuestionRepository questionRepository;

    @Before
    public void before() throws Exception {
        Question question = new Question();
        questionRepository.save(question);
    }

    @Test
    public void exceptionHandlerTest() throws Exception {
        this.mockMvc.perform(get("/question/100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTest() throws Exception {
        this.mockMvc.perform(get("/question"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdTest() throws Exception {
        this.mockMvc.perform(get("/question/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void postTest() throws Exception {
        this.mockMvc.perform(post("/question")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"question\":null," +
                        "\"exam_id\":null," +
                        "\"reports\":[]," +
                        "\"answers\":[]}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void putTest() throws Exception {
        this.mockMvc.perform(put("/question/1")
                .content("{\"id\":1," +
                        "\"question\":null," +
                        "\"exam_id\":null," +
                        "\"reports\":[]," +
                        "\"answers\":[]}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTest() throws Exception {
        this.mockMvc.perform(delete("/question/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
