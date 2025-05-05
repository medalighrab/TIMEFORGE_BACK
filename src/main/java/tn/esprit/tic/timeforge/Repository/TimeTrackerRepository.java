package tn.esprit.tic.timeforge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tic.timeforge.Entity.TimeTracker;

public interface TimeTrackerRepository extends JpaRepository<TimeTracker, Long> {
    // PAS de méthode findByEventDate ici ❌
}
