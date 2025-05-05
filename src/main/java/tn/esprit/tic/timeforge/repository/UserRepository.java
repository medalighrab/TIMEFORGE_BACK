package tn.esprit.tic.timeforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.entity.Role;
import tn.esprit.tic.timeforge.entity.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        Set<User> findAllByRoles(Role role);
        Optional<User> findByUsername(String username);


    Optional<User> findByResetpasswordtoken(String token);
}
