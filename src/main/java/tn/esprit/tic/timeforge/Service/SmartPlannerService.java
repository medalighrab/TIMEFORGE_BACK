package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.CalendarIntegration;
import tn.esprit.tic.timeforge.Entity.TimeManagementTechniques;
import tn.esprit.tic.timeforge.Entity.TimeTracker;
import tn.esprit.tic.timeforge.Repository.CalendarIntegrationRepository;
import tn.esprit.tic.timeforge.Repository.TimeManagementTechniquesRepository;
import tn.esprit.tic.timeforge.Repository.TimeTrackerRepository;
import tn.esprit.tic.timeforge.dto.SessionPlan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SmartPlannerService {

    private final TimeTrackerRepository timeTrackerRepository;
    private final CalendarIntegrationRepository calendarIntegrationRepository;
    private final TimeManagementTechniquesRepository timeManagementTechniquesRepository;

    public List<SessionPlan> generateSmartPlan() {
        List<TimeTracker> sessions = timeTrackerRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(this::priorityOrder))
                .collect(Collectors.toList());

        List<CalendarIntegration> calendarEvents = calendarIntegrationRepository.findAll();
        List<TimeManagementTechniques> techniques = timeManagementTechniquesRepository.findAll();

        List<SessionPlan> plan = new ArrayList<>();

        Map<LocalDate, List<CalendarIntegration>> eventsByDate = calendarEvents.stream()
                .filter(e -> e.getEventDate() != null)
                .collect(Collectors.groupingBy(CalendarIntegration::getEventDate));

        LocalDate today = LocalDate.now();

        for (TimeTracker session : sessions) {
            // Rechercher une journée avec le plus de temps libre
            Optional<LocalDate> availableDate = findAvailableDate(eventsByDate, today, session.getEstimatedMinutes());

            availableDate.ifPresent(date -> {
                LocalTime startTime = findAvailableStartTime(eventsByDate.getOrDefault(date, new ArrayList<>()));
                LocalTime endTime = startTime.plusMinutes(session.getEstimatedMinutes());

                // Trouver une technique adaptée
                String technique = findBestTechnique(techniques, session.getEstimatedMinutes());

                // Ajouter au plan
                SessionPlan sessionPlan = new SessionPlan();
                sessionPlan.setSessionTitle(session.getTitle());
                sessionPlan.setTechniqueName(technique);
                sessionPlan.setDate(date);
                sessionPlan.setStartTime(startTime);
                sessionPlan.setEndTime(endTime);

                plan.add(sessionPlan);

                // Bloquer l'intervalle dans eventsByDate pour les prochaines recherches
                // Bloquer l'intervalle dans eventsByDate pour les prochaines recherches
                CalendarIntegration newEvent = new CalendarIntegration();
                newEvent.setEventDate(date);
                newEvent.setStartTime(startTime); // correction ici
                newEvent.setEndTime(endTime);     // correction ici
                eventsByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(newEvent);

            });
        }

        return plan;
    }

    private Optional<LocalDate> findAvailableDate(Map<LocalDate, List<CalendarIntegration>> eventsByDate, LocalDate startDate, int estimatedMinutes) {
        for (int i = 0; i < 7; i++) { // Cherche dans les 7 prochains jours
            LocalDate date = startDate.plusDays(i);
            List<CalendarIntegration> events = eventsByDate.getOrDefault(date, new ArrayList<>());

            // Si journée avec au moins un créneau de 2h vide
            if (findAvailableStartTime(events) != null) {
                return Optional.of(date);
            }
        }
        return Optional.empty();
    }

    private LocalTime findAvailableStartTime(List<CalendarIntegration> events) {
        List<LocalTime[]> occupiedSlots = events.stream()
                .filter(e -> e.getStartTime() != null && e.getEndTime() != null)
                .map(e -> new LocalTime[]{
                        e.getStartTime(),
                        e.getEndTime()
                })
                .sorted(Comparator.comparing(a -> a[0]))
                .collect(Collectors.toList());

        LocalTime dayStart = LocalTime.of(8, 0);
        LocalTime dayEnd = LocalTime.of(20, 0);

        if (occupiedSlots.isEmpty()) {
            return dayStart;
        }

        for (int i = 0; i < occupiedSlots.size(); i++) {
            if (i == 0 && occupiedSlots.get(0)[0].isAfter(dayStart)) {
                return dayStart;
            }

            if (i < occupiedSlots.size() - 1) {
                if (occupiedSlots.get(i)[1].isBefore(occupiedSlots.get(i + 1)[0])) {
                    return occupiedSlots.get(i)[1];
                }
            } else {
                if (occupiedSlots.get(i)[1].isBefore(dayEnd)) {
                    return occupiedSlots.get(i)[1];
                }
            }
        }

        return null;
    }

    private String findBestTechnique(List<TimeManagementTechniques> techniques, int estimatedMinutes) {
        if (techniques.isEmpty()) {
            return "Technique Générique";
        }
        // Simple random pour l'instant
        return techniques.get(new Random().nextInt(techniques.size())).getName();
    }
    private int priorityOrder(TimeTracker t) {
        if (t.getPriority() == null) return 4; // si priorité non définie

        switch (t.getPriority()) {
            case "Haute": return 1;
            case "Moyenne": return 2;
            case "Basse": return 3;
            default: return 4;
        }
    }
    public void recalculatePlanAfterNewEvent() {
        // Ici on peut soit :
        // - recalculer tout le planning
        // - ou juste marquer que le planning doit être régénéré

        // Exemple simple : recalculer tout
        List<SessionPlan> newPlan = generateSmartPlan();
        // Tu peux ici sauver newPlan en base si besoin
        System.out.println("✅ Replanification automatique effectuée : " + newPlan.size() + " sessions reprogrammées.");
    }


}
