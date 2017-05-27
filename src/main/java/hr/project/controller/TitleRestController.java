package hr.project.controller;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Statistic;
import hr.project.model.Title;
import hr.project.repository.StatisticRepository;
import hr.project.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Title titleById(@PathVariable Integer id) {
        Title title = titleRepository.findById(id);
        if (title == null) { throw new ObjectNotFound(id); }
        return title;
    }
}
