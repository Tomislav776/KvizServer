package hr.project.controller;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Report;
import hr.project.model.Role;
import hr.project.model.Statistic;
import hr.project.repository.RoleRepository;
import hr.project.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/statistic")
public class StatisticRestController {

    private final StatisticRepository statisticRepository;

    @Autowired
    StatisticRestController(StatisticRepository statisticRepository) {this.statisticRepository = statisticRepository;}

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Statistic statisticById(@PathVariable Integer id) {
        Statistic statistic = statisticRepository.findById(id);
        if (statistic == null) { throw new ObjectNotFound(id); }
        return statistic;
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Statistic> saveStatistic(@RequestBody Statistic statistic, UriComponentsBuilder ucb) {
        statistic = statisticRepository.save(statistic);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/statistic/").path(String.valueOf(statistic.getId())).build().toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(statistic, headers, HttpStatus.CREATED);
    }
}
