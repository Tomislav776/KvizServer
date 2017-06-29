package hr.project.controller;

import hr.project.model.Exam;
import hr.project.repository.ExamRepository;
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
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ExamRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ExamRepository examRepository;

    @Before
    public void before() {
        Exam exam = new Exam();
        examRepository.save(exam);
    }

    @Test
    public void exceptionHandlerTest() throws Exception {
        this.mockMvc.perform(get("/exam/100")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void getTest() throws Exception {
        this.mockMvc.perform(get("/exam/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getByIdTest() throws Exception {
        this.mockMvc.perform(get("/exam/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void postTest() throws Exception {
        this.mockMvc.perform(post("/exam/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":null," +
                        "\"subject_id\":null," +
                        "\"questions\":[]}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void putTest() throws Exception {
        this.mockMvc.perform(put("/exam/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1," +
                        "\"name\":null," +
                        "\"subject_id\":null," +
                        "\"questions\":[]}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTest() throws Exception {
        this.mockMvc.perform(delete("/exam/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
