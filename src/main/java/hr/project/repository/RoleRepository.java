package hr.project.repository;

import hr.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findById(Integer id);
}
