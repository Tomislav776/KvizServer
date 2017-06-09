package hr.project.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AnswerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnswerRestController answerRestController;

    @Test
    public void findAllTest(){
        assertThat(answerRestController.findAll()).isNotNull();
    }

    @Test
    public void postTest() throws Exception {
        this.mockMvc.perform(post("/answer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": 3,\n" +
                        "    \"answer\": \"255\",\n" +
                        "    \"correct\": false,\n" +
                        "    \"question_id\": 1\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getTest() throws Exception {
        this.mockMvc.perform(get("/answer")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(notNullValue()));
    }


}
