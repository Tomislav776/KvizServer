package hr.project.controller;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Report;
import hr.project.model.Role;
import hr.project.repository.ReportRepository;
import hr.project.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleRestController {

    private final RoleRepository roleRepository;

    @Autowired
    RoleRestController(RoleRepository roleRepository) {this.roleRepository = roleRepository;}

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Role roleById(@PathVariable Integer id) {
        Role role = roleRepository.findById(id);
        if (role == null) { throw new ObjectNotFound(id); }
        return role;
    }
}
