package hr.project.repository;

import hr.project.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
    Game findById(Integer id);
}
