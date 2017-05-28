package hr.project.repository;

import hr.project.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface StatisticRepository extends JpaRepository<Statistic, Integer> {

    Statistic findById(Integer id);
}
