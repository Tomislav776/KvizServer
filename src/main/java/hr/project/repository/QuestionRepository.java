package hr.project.repository;

import hr.project.model.Exam;
import hr.project.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findById(Integer id);

    @Query("SELECT o FROM Question o JOIN FETCH o.answers i WHERE o.id = ?1")
    Question setSingleResultById(Integer id);
    /*@Modifying
    @Query("update Question u set u.answers = ?1 where u.lastname = ?2")
    int setFixedFirstnameFor(String firstname, String lastname);*/
}
