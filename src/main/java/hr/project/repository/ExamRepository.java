package hr.project.repository;

import hr.project.model.Course;
import hr.project.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    Exam findById(Integer id);

}
