package hr.project.controller;


import hr.project.model.Smjer;
import hr.project.repository.SmjerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/smjer")
public class SmjerRestController {



        private final SmjerRepository smjerRepository;

        @Autowired
        SmjerRestController(SmjerRepository smjerRepository) {
            this.smjerRepository = smjerRepository;
        }


        @RequestMapping(method = RequestMethod.GET, value = "/{smjerId}")
        Smjer readSmjer(@PathVariable Integer smjerId) {
            return this.smjerRepository.findOne(smjerId);
        }

}
