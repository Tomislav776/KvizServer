package hr.project.repository;

import hr.project.model.Smjer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;


public interface SmjerRepository extends JpaRepository<Smjer, Integer> {

    Collection<Smjer> findByNaziv(String naziv);
}

