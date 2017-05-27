package hr.project.controller;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Course;
import hr.project.model.Exam;
import hr.project.repository.CourseRepository;
import hr.project.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
public class ExamRestController {
    private final ExamRepository examRepository;

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

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Exam examById(@PathVariable Integer id) {
        Exam exam = examRepository.findById(id);
        if (exam == null) { throw new ObjectNotFound(id); }
        return exam;
    }

}
