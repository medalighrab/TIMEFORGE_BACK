package tn.esprit.tic.timeforge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.Goals;
@Repository
public interface GoalsRepository extends JpaRepository<Goals, Long> {
}
