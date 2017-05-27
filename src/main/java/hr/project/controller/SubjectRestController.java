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

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Subject subjectById(@PathVariable Integer id) {
        Subject subject = subjectRepository.findById(id);
        if (subject == null) { throw new ObjectNotFound(id); }
        return subject;
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Subject> saveSubject(@RequestBody Subject subject, UriComponentsBuilder ucb) {
        subject = subjectRepository.save(subject);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/subject/").path(String.valueOf(subject.getId())).build().toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(subject, headers, HttpStatus.CREATED);
    }

}
