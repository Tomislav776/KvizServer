package hr.project.repository;

import hr.project.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findById(Integer id);
}
