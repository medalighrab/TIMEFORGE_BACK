package tn.esprit.tic.timeforge.Reposotory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.Supervisor;
import tn.esprit.tic.timeforge.Entity.User;
@Repository
public interface SupervisorRepo  extends JpaRepository<Supervisor, Long> {
}
