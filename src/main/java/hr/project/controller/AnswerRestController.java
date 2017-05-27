package hr.project.controller;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Answer;
import hr.project.model.Course;
import hr.project.repository.AnswerRepository;
import hr.project.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/answer")
public class AnswerRestController {

    private final AnswerRepository answerRepository;

    @Autowired
    AnswerRestController(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Answer answerById(@PathVariable Integer id) {
        Answer answer = answerRepository.findById(id);
        if (answer == null) { throw new ObjectNotFound(id); }
        return answer;
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Answer> saveAnswer(@RequestBody Answer answer, UriComponentsBuilder ucb) {
        answer = answerRepository.save(answer);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/answer/").path(String.valueOf(answer.getId())).build().toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(answer, headers, HttpStatus.CREATED);
    }
}
