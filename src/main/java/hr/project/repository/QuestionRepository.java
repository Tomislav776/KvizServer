package hr.project.repository;

import hr.project.model.Exam;
import hr.project.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findById(Integer id);

    List<Question> findAllByexam_id(Integer id);

    /*@Modifying
    @Query("update Question u set u.answers = ?1 where u.lastname = ?2")
    int setFixedFirstnameFor(String firstname, String lastname);*/
}
