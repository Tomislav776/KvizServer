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
import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswerRestController {
/*/dsfds*/
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

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Answer>> findAll() {
        List<Answer> answers = answerRepository.findAll();
        if (answers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(answers);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Answer> findById(@PathVariable Integer id) {
        Answer answer = answerRepository.findById(id);
        if (answer == null) { throw new ObjectNotFound(id); }
        return ResponseEntity.ok(answer);
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Answer> save(@RequestBody Answer answer, UriComponentsBuilder ucb) {
        HttpHeaders headers = new HttpHeaders();

        try{
            answer = answerRepository.save(answer);
            URI locationUri = ucb.path("/answer/").path(String.valueOf(answer.getId())).build().toUri();
            headers.setLocation(locationUri);
            return new ResponseEntity<>(answer, headers, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(answer);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Answer answer,@PathVariable Integer id)  {

        if (!(answerRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }
        answer.setId(id);
        answerRepository.save(answer);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            answerRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }

}
