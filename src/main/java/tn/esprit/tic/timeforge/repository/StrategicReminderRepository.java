package tn.esprit.tic.timeforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.entity.StrategicReminder;
@Repository
public interface StrategicReminderRepository extends JpaRepository<StrategicReminder, Long> {
}
