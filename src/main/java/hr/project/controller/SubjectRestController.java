package hr.project.controller;


import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Course;
import hr.project.model.Subject;
import hr.project.repository.CourseRepository;
import hr.project.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectRestController {
    private final SubjectRepository subjectRepository;

    @Autowired
    SubjectRestController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error courseNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Subject>> findAll() {
        List<Subject> subjects = subjectRepository.findAll();
        if (subjects.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subjects);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Subject> findById(@PathVariable Integer id) {
        Subject subject = subjectRepository.findById(id);
        if (subject == null) { throw new ObjectNotFound(id); }
        return ResponseEntity.ok(subject);
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Subject> save(@RequestBody Subject subject, UriComponentsBuilder ucb) {
        HttpHeaders headers = new HttpHeaders();

        try{
            subject = subjectRepository.save(subject);
            URI locationUri = ucb.path("/subject/").path(String.valueOf(subject.getId())).build().toUri();
            headers.setLocation(locationUri);
            return new ResponseEntity<>(subject, headers, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(subject);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Subject subject,@PathVariable Integer id)  {

        if (!(subjectRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }
        subject.setId(id);
        subjectRepository.save(subject);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            subjectRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }

}
