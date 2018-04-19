package hr.project.repository;

import hr.project.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findById(Integer id);

    List<Question> findAllByexam_id(Integer id);

    @Modifying
    @Query("UPDATE Question q SET q.verified = :verified WHERE q.id = :questionId")
    void updateVerifiedStatus(@Param("questionId") int questionId, @Param("verified") boolean verified);
}
