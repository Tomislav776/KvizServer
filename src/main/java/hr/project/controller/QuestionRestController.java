package hr.project.controller;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Game;
import hr.project.model.Question;
import hr.project.repository.GameRepository;
import hr.project.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/question")
public class QuestionRestController {
    private final QuestionRepository questionRepository;

    @Autowired
    QuestionRestController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Question questionById(@PathVariable Integer id) {
        Question question = questionRepository.findById(id);
        if (question == null) { throw new ObjectNotFound(id); }
        return question;
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Question> saveQuestion(@RequestBody Question question, UriComponentsBuilder ucb) {
        question = questionRepository.save(question);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/question/").path(String.valueOf(question.getId())).build().toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(question, headers, HttpStatus.CREATED);
    }
}
