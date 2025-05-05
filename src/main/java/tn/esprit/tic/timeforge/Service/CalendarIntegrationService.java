package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.CalendarIntegration;
import tn.esprit.tic.timeforge.Repository.CalendarIntegrationRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalendarIntegrationService {

    private final CalendarIntegrationRepository repository;

    public List<CalendarIntegration> getAll() {
        return repository.findAll();
    }

    public Optional<CalendarIntegration> getById(Long id) {
        return repository.findById(id);
    }

    public CalendarIntegration create(CalendarIntegration event) {
        return repository.save(event);
    }

    public Optional<CalendarIntegration> update(Long id, CalendarIntegration updated) {
        return repository.findById(id).map(event -> {
            event.setEventTitle(updated.getEventTitle());
            event.setEventDate(updated.getEventDate());
            event.setStartTime(updated.getStartTime());
            event.setEndTime(updated.getEndTime());
            event.setLocation(updated.getLocation());
            event.setDescription(updated.getDescription());
            event.setTimeTracker(updated.getTimeTracker());
            return repository.save(event);
        });
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
