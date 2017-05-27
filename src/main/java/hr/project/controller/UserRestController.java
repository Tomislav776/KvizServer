package hr.project.controller;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Statistic;
import hr.project.model.Title;
import hr.project.model.User;
import hr.project.repository.TitleRepository;
import hr.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserRepository userRepository;

    @Autowired
    UserRestController(UserRepository userRepository) {this.userRepository = userRepository;}

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public User userById(@PathVariable Integer id) {
        User user = userRepository.findById(id);
        if (user == null) { throw new ObjectNotFound(id); }
        return user;
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<User> saveUser(@RequestBody User user, UriComponentsBuilder ucb) {
        user = userRepository.save(user);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/user/").path(String.valueOf(user.getId())).build().toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }
}
