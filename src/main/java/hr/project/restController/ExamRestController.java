package hr.project.restController;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Exam;
import hr.project.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamRestController {
    private final ExamRepository examRepository;

    /*private EntityManager entityManager;

    @PersistenceContext
    private EntityManager em;
    */

    @Autowired
    ExamRestController(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Exam>> findAll() {
        List<Exam> exams = examRepository.findAll();
        if (exams.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(exams);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Exam> findById(@PathVariable Integer id) {
        Exam exam = examRepository.findById(id);
        //Exam exam = examRepository.setSingleResultById(id);

        if (exam == null) { throw new ObjectNotFound(id); }
        return ResponseEntity.ok(exam);
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Exam> save(@RequestBody Exam exam, UriComponentsBuilder ucb) {
        HttpHeaders headers = new HttpHeaders();

        try{
            exam = examRepository.save(exam);
            URI locationUri = ucb.path("/exam/").path(String.valueOf(exam.getId())).build().toUri();
            headers.setLocation(locationUri);
            return new ResponseEntity<>(exam, headers, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exam);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Exam exam,@PathVariable Integer id)  {

        if (!(examRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }
        exam.setId(id);
        examRepository.save(exam);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            examRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }

}
