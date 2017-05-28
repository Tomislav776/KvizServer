package hr.project.repository;

import hr.project.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GameRepository extends JpaRepository<Game, Integer> {
    Game findById(Integer id);
}
