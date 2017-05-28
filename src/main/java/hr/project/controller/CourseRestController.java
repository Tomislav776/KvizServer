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
import java.util.List;

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

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Course>> findAll() {
        List<Course> courses = courseRepository.findAll();
        if (courses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(courses);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Course> findById(@PathVariable Integer id) {
        Course course = courseRepository.findById(id);
        if (course == null) { throw new ObjectNotFound(id); }
        return ResponseEntity.ok(course);
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Course> save(@RequestBody Course course, UriComponentsBuilder ucb) {
        HttpHeaders headers = new HttpHeaders();

        try{
            course = courseRepository.save(course);
            URI locationUri = ucb.path("/course/").path(String.valueOf(course.getId())).build().toUri();
            headers.setLocation(locationUri);
            return new ResponseEntity<>(course, headers, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(course);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Course course,@PathVariable Integer id)  {

        if (!(courseRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }
        course.setId(id);
        courseRepository.save(course);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            courseRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }


}


