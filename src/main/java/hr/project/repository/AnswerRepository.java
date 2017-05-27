package hr.project.repository;

import hr.project.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    Answer findById(Integer id);
}
