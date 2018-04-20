package hr.project.service.serviceImpl;

import hr.project.model.Course;
import hr.project.repository.CourseRepository;
import hr.project.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }
}
