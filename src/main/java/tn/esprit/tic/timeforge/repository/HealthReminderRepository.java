package tn.esprit.tic.timeforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.entity.HealthReminder;
@Repository
public interface HealthReminderRepository extends JpaRepository<HealthReminder, Long> {
}
