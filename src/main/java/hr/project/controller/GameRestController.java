package hr.project.controller;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Exam;
import hr.project.model.Game;
import hr.project.model.Subject;
import hr.project.repository.ExamRepository;
import hr.project.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameRestController {
    private final GameRepository gameRepository;

    @Autowired
    GameRestController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Game>> findAll() {
        List<Game> games = gameRepository.findAll();
        if (games.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(games);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Game> findById(@PathVariable Integer id) {
        Game game = gameRepository.findById(id);
        if (game == null) { throw new ObjectNotFound(id); }
        return ResponseEntity.ok(game);
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Game> save(@RequestBody Game game, UriComponentsBuilder ucb) {
        HttpHeaders headers = new HttpHeaders();

        try{
            game = gameRepository.save(game);
            URI locationUri = ucb.path("/game/").path(String.valueOf(game.getId())).build().toUri();
            headers.setLocation(locationUri);
            return new ResponseEntity<>(game, headers, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(game);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Game game,@PathVariable Integer id)  {

        if (!(gameRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }
        game.setId(id);
        gameRepository.save(game);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            gameRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }
}
