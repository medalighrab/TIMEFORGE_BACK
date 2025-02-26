package tn.esprit.tic.timeforge.Reposotory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.Role;
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);



}
