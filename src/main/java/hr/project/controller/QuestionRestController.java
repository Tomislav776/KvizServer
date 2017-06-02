package hr.project.controller;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Game;
import hr.project.model.Question;
import hr.project.repository.GameRepository;
import hr.project.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Question>> findAll() {
        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questions);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Question> findById(@PathVariable Integer id) {
        Question question = questionRepository.findById(id);
        //Question question = questionRepository.setSingleResultById(id);
        if (question == null) { throw new ObjectNotFound(id); }
        return ResponseEntity.ok(question);
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Question> saveQuestion(@RequestBody Question question, UriComponentsBuilder ucb) {
        HttpHeaders headers = new HttpHeaders();

        try{
            question = questionRepository.save(question);
            URI locationUri = ucb.path("/question/").path(String.valueOf(question.getId())).build().toUri();
            headers.setLocation(locationUri);
            return new ResponseEntity<>(question, headers, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(question);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Question question,@PathVariable Integer id)  {

        if (!(questionRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }
        question.setId(id);
        questionRepository.save(question);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            questionRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }

}