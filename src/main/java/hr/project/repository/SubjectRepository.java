package hr.project.repository;

import hr.project.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    Subject findById(Integer id);
}
