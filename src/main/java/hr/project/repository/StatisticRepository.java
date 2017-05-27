package hr.project.repository;

import hr.project.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StatisticRepository extends JpaRepository<Statistic, Integer> {

    Statistic findById(Integer id);
}
