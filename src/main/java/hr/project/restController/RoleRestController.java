package hr.project.restController;

import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.Role;
import hr.project.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Role>> findAll() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roles);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Role> findById(@PathVariable Integer id) {
        Role role = roleRepository.findById(id);
        if (role == null) { throw new ObjectNotFound(id); }
        return ResponseEntity.ok(role);
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Role> save(@RequestBody Role role, UriComponentsBuilder ucb) {
        HttpHeaders headers = new HttpHeaders();

        try{
            role = roleRepository.save(role);
            URI locationUri = ucb.path("/role/").path(String.valueOf(role.getId())).build().toUri();
            headers.setLocation(locationUri);
            return new ResponseEntity<>(role, headers, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(role);
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Role role,@PathVariable Integer id)  {

        if (!(roleRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }
        role.setId(id);
        roleRepository.save(role);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            roleRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }
}
