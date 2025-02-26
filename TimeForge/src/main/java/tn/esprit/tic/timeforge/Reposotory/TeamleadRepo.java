package tn.esprit.tic.timeforge.Reposotory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.Teamlead;
import tn.esprit.tic.timeforge.Entity.User;
@Repository
public interface TeamleadRepo  extends JpaRepository<Teamlead, Long> {
}
