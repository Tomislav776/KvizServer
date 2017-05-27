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

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Game gameById(@PathVariable Integer id) {
        Game game = gameRepository.findById(id);
        if (game == null) { throw new ObjectNotFound(id); }
        return game;
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Game> saveSubject(@RequestBody Game game, UriComponentsBuilder ucb) {
        game = gameRepository.save(game);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/game/").path(String.valueOf(game.getId())).build().toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(game, headers, HttpStatus.CREATED);
    }
}
