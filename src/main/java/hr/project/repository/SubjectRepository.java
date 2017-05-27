package hr.project.repository;

import hr.project.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    Subject findById(Integer id);
}
