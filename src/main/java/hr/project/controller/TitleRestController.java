package hr.project.controller;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Statistic;
import hr.project.model.Title;
import hr.project.repository.StatisticRepository;
import hr.project.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/title")
public class TitleRestController {

    private final TitleRepository titleRepository;

    @Autowired
    TitleRestController(TitleRepository titleRepository) {this.titleRepository = titleRepository;}

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Title>> findAll() {
        List<Title> titles = titleRepository.findAll();
        if (titles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(titles);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Title> findById(@PathVariable Integer id) {
        Title title = titleRepository.findById(id);
        if (title == null) { throw new ObjectNotFound(id); }
        return ResponseEntity.ok(title);
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Title> save(@RequestBody Title title, UriComponentsBuilder ucb) {
        HttpHeaders headers = new HttpHeaders();

        try{
            title = titleRepository.save(title);
            URI locationUri = ucb.path("/title/").path(String.valueOf(title.getId())).build().toUri();
            headers.setLocation(locationUri);
            return new ResponseEntity<>(title, headers, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(title);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Title title,@PathVariable Integer id)  {

        if (!(titleRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }
        title.setId(id);
        titleRepository.save(title);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            titleRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }
}
