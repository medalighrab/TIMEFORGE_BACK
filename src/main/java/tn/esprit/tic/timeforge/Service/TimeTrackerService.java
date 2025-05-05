package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.TimeTracker;
import tn.esprit.tic.timeforge.Repository.TimeTrackerRepository;
import tn.esprit.tic.timeforge.Entity.CalendarIntegration;
import tn.esprit.tic.timeforge.Repository.CalendarIntegrationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeTrackerService {

    private final TimeTrackerRepository repository;
    private final CalendarIntegrationRepository calendarRepository; // ✅ Pour récupérer les événements existants

    // ✅ CRUD classique
    public List<TimeTracker> getAll() {
        return repository.findAll();
    }

    public Optional<TimeTracker> getById(Long id) {
        return repository.findById(id);
    }

    public TimeTracker create(TimeTracker tracker) {
        tracker.setCreationDate(LocalDate.now()); // Ajoute la date de création
        return repository.save(tracker);
    }

    public Optional<TimeTracker> update(Long id, TimeTracker updatedTracker) {
        return repository.findById(id).map(tracker -> {
            tracker.setTitle(updatedTracker.getTitle());
            tracker.setDescription(updatedTracker.getDescription());
            tracker.setEstimatedMinutes(updatedTracker.getEstimatedMinutes());
            tracker.setCompleted(updatedTracker.isCompleted());
            tracker.setStartTime(updatedTracker.getStartTime());
            tracker.setUser(updatedTracker.getUser());
            return repository.save(tracker);
        });
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    // ✅ Nouvelle Fonction Scheduler (Planification automatique)
    public Optional<TimeTracker> schedule(TimeTracker tracker) {
        LocalDate today = LocalDate.now();

        // 1. Récupérer les événements de calendrier existants pour aujourd'hui
        List<CalendarIntegration> eventsToday = calendarRepository.findByEventDate(today);

        // 2. Trouver l'heure de fin du dernier événement de la journée
        LocalTime latestEnd = eventsToday.stream()
                .map(CalendarIntegration::getEndTime)
                .max(LocalTime::compareTo)
                .orElse(LocalTime.of(8, 0)); // S'il n'y a pas d'événements ➔ Commencer à 8h00

        // 3. Planifier 30 minutes après
        LocalTime suggestedStart = latestEnd.plusMinutes(30);

        // 4. Remplir les informations automatiques
        tracker.setStartTime(suggestedStart);
        tracker.setCreationDate(today);
        tracker.setCompleted(false); // Par défaut : non terminé

        // 5. Enregistrer dans la base
        return Optional.of(repository.save(tracker));
    }
}
