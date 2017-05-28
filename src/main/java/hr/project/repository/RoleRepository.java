package hr.project.repository;

import hr.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findById(Integer id);
}
