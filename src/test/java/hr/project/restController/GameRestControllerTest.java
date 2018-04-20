package hr.project.restController;

import hr.project.model.Game;
import hr.project.repository.GameRepository;
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
public class GameRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    GameRepository gameRepository;

    @Before
    public void before() {
        Game game = new Game();
        gameRepository.save(game);
    }

    @Test
    public void exceptionHandlerTest() throws Exception {
        this.mockMvc.perform(get("/game/100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTest() throws Exception {
        this.mockMvc.perform(get("/game/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdTest() throws Exception {
        this.mockMvc.perform(get("/game/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void postTest() throws Exception {
        this.mockMvc.perform(post("/game")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"score\":null," +
                        "\"user1_points\":null," +
                        "\"user2_points\":null," +
                        "\"subject_id\":null," +
                        "\"user1_id\":null," +
                        "\"user2_id\":null}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void putTest() throws Exception {
        this.mockMvc.perform(put("/game/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1" +
                        ",\"score\":null," +
                        "\"user1_points\":null," +
                        "\"user2_points\":null," +
                        "\"subject_id\":null," +
                        "\"user1_id\":null," +
                        "\"user2_id\":null}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTest() throws Exception {
        this.mockMvc.perform(delete("/game/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
