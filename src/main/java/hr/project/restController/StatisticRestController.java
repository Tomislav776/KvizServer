package hr.project.restController;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Statistic;
import hr.project.repository.StatisticRepository;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticRestController {

    private final StatisticRepository statisticRepository;

    @PersistenceContext
    private EntityManager em;

    private Session configurarSessao() {
        return  em.unwrap(org.hibernate.Session.class);
    }

    @Autowired
    StatisticRestController(StatisticRepository statisticRepository) {this.statisticRepository = statisticRepository;}

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Statistic>> findAll() {
        List<Statistic> statistics = statisticRepository.findAll();
        if (statistics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(statistics);
    }

    @RequestMapping(value="/{user_id}/{subject_id}", method=RequestMethod.GET)
    public ResponseEntity<Statistic> getUserStatistic(@PathVariable Integer user_id, @PathVariable Integer subject_id) {
        List<Statistic> statistics = configurarSessao().createQuery("select s from Statistic s where s.user_id = user_id and s.subject_id = subject_id")
                .list();

        if (statistics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Statistic statistic = statistics.get(0);

        return ResponseEntity.ok(statistic);
    }

    @RequestMapping(value="/getScoreTable/{subject_id}", method=RequestMethod.GET)
    public ResponseEntity<List<Statistic>> getScoreTable(@PathVariable Integer subject_id) {
        List<Statistic> statistics = configurarSessao().createQuery("select s.points as points, u.name as username from Statistic s inner join s.user u where s.subject_id = subject_id order by points desc ")
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .list();

        if (statistics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(statistics);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Statistic> findById(@PathVariable Integer id) {
        Statistic statistic = statisticRepository.findById(id);
        if (statistic == null) { throw new ObjectNotFound(id); }
        return ResponseEntity.ok(statistic);
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Statistic> save(@RequestBody Statistic statistic, UriComponentsBuilder ucb) {
        HttpHeaders headers = new HttpHeaders();

        try{
            statistic = statisticRepository.save(statistic);
            URI locationUri = ucb.path("/statistic/").path(String.valueOf(statistic.getId())).build().toUri();
            headers.setLocation(locationUri);
            return new ResponseEntity<>(statistic, headers, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(statistic);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Statistic statistic,@PathVariable Integer id)  {

        if (!(statisticRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }
        statistic.setId(id);
        statisticRepository.save(statistic);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            statisticRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }
}
