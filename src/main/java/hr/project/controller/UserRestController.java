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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.URI;
import java.util.List;

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

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        User user = userRepository.findById(id);
        if (user == null) { throw new ObjectNotFound(id); }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value="/login", method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<User> login(@RequestBody User user, UriComponentsBuilder ucb) {
        User userInDB = userRepository.findById(user.getId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(user.getPassword(), userInDB.getPassword())) {
            return new ResponseEntity<>(userInDB, null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, null, HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<User> save(@RequestBody User user, UriComponentsBuilder ucb) {
        user.setPassword(new BCryptPasswordEncoder(15).encode(user.getPassword()));

        try {
            user = userRepository.save(user);
            return new ResponseEntity<>(user, null, HttpStatus.CREATED);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody User user,@PathVariable Integer id)  {

        if (!(userRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            userRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }
}
