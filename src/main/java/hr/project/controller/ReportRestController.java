package hr.project.controller;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Question;
import hr.project.model.Report;
import hr.project.repository.QuestionRepository;
import hr.project.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Report reportById(@PathVariable Integer id) {
        Report report = reportRepository.findById(id);
        if (report == null) { throw new ObjectNotFound(id); }
        return report;
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Report> saveReport(@RequestBody Report report, UriComponentsBuilder ucb) {
        report = reportRepository.save(report);
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/report/").path(String.valueOf(report.getId())).build().toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(report, headers, HttpStatus.CREATED);
    }
}
