package hr.project.controller;

import hr.project.model.Course;
import hr.project.model.Subject;
import hr.project.repository.CourseRepository;
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CourseRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CourseRepository courseRepository;

    @Before
    public void before() {
        Course course = new Course();
        courseRepository.save(course);
    }

    @Test
    public void getTest() throws Exception {
        this.mockMvc.perform(get("/course/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getByIdTest() throws Exception {
        this.mockMvc.perform(get("/course/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void postTest() throws Exception {
        this.mockMvc.perform(post("/course/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":null," +
                        "\"subjects\":[]}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void putTest() throws Exception {
        Course course = courseRepository.findById(1);
        this.mockMvc.perform(put("/course/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":"+ course.getId() +"," +
                        "\"name\":"+ course.getName() +"," +
                        "\"subjects\":[]}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTest() throws Exception {
        this.mockMvc.perform(delete("/course/1")).andDo(print()).andExpect(status().isOk());
    }
}
