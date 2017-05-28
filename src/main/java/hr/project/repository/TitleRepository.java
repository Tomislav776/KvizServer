package hr.project.repository;

import hr.project.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TitleRepository extends JpaRepository<Title, Integer> {

    Title findById(Integer id);
}
