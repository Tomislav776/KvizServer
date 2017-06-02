package hr.project.repository;

import hr.project.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    Exam findById(Integer id);

    @Query("SELECT o FROM Exam o JOIN FETCH o.questions i WHERE o.id = ?1")
    Exam setSingleResultById(Integer id);

}
