package tn.esprit.tic.timeforge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tic.timeforge.Entity.CalendarIntegration;

import java.time.LocalDate;
import java.util.List;

public interface CalendarIntegrationRepository extends JpaRepository<CalendarIntegration, Long> {

    List<CalendarIntegration> findByEventDate(LocalDate eventDate);
}
