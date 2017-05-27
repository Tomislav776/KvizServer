package hr.project.repository;

import hr.project.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    Course findById(Integer id);
}

