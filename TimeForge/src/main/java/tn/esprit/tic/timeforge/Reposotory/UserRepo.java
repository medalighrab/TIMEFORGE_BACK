package tn.esprit.tic.timeforge.Reposotory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.User;

import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByMail(String mail);
    User findFirstByMail (String mail);
//    boolean existsByUsername(String username);
//    boolean existsByEmail(String email);
//    User findByUsername(String username);
}
