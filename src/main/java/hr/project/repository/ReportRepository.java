package hr.project.repository;

import hr.project.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReportRepository extends JpaRepository<Report, Integer> {

    Report findById(Integer id);
}
