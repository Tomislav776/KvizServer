package hr.project.controller;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Course;
import hr.project.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/course")
public class CourseRestController {

    private final CourseRepository courseRepository;

    @Autowired
    CourseRestController(CourseRepository courseRepository) {
            this.courseRepository = courseRepository;
        }

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Course courseById(@PathVariable Integer id) {
        Course course = courseRepository.findById(id);
        if (course == null) { throw new ObjectNotFound(id); }
        return course;
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Course> saveCourse(@RequestBody Course course, UriComponentsBuilder ucb) {
        course = courseRepository.save(course);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/course/").path(String.valueOf(course.getId())).build().toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(course, headers, HttpStatus.CREATED);
    }


}


