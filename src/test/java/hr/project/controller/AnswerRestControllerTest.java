package hr.project.controller;

import hr.project.model.Answer;
import hr.project.repository.AnswerRepository;
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

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AnswerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnswerRepository answerRepository;

    @Before
    public void before(){
        Answer answer = new Answer();
        answerRepository.save(answer);
    }

    @Test
    public void postTest() throws Exception {
        this.mockMvc.perform(post("/answer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"answer\":null,\"correct\":false,\"question_id\":null}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void postBadTest() throws Exception {
        this.mockMvc.perform(post("/answer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getTest() throws Exception {
        this.mockMvc.perform(get("/answer")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(notNullValue()));
    }

    @Test
    public void getIdTest() throws Exception {
        this.mockMvc.perform(get("/answer/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void putTest() throws Exception {
        Answer answer = answerRepository.findById(1);
        this.mockMvc.perform(put("/answer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": "+ answer.getId() +",\n" +
                        "    \"answer\": "+ answer.getAnswer() +",\n" +
                        "    \"correct\": "+ answer.isCorrect() +",\n" +
                        "    \"question_id\": "+ answer.getQuestion_id() +"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTest() throws Exception {
        this.mockMvc.perform(delete("/answer/1")).andExpect(status().isOk());
    }
}
