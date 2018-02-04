package hr.project.restController;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Report;
import hr.project.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportRestController {

    private final ReportRepository reportRepository;

    @Autowired
    ReportRestController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Report>> findAll() {
        List<Report> reports = reportRepository.findAll();
        if (reports.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reports);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Report> findById(@PathVariable Integer id) {
        Report report = reportRepository.findById(id);
        if (report == null) { throw new ObjectNotFound(id); }
        return ResponseEntity.ok(report);
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Report> save(@RequestBody Report report, UriComponentsBuilder ucb) {
        HttpHeaders headers = new HttpHeaders();

        try{
            report = reportRepository.save(report);
            URI locationUri = ucb.path("/report/").path(String.valueOf(report.getId())).build().toUri();
            headers.setLocation(locationUri);
            return new ResponseEntity<>(report, headers, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(report);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Report report,@PathVariable Integer id)  {

        if (!(reportRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }
        report.setId(id);
        reportRepository.save(report);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            reportRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }
}
