package hr.project.repository;

import hr.project.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Integer> {

    Title findById(Integer id);
}
