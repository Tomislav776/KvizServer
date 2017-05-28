package hr.project.repository;

import hr.project.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Course findById(Integer id);
}

