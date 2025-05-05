package tn.esprit.tic.timeforge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.CalendarIntegration;
import tn.esprit.tic.timeforge.Repository.CalendarIntegrationRepository;
import tn.esprit.tic.timeforge.Service.CalendarIntegrationService;
import tn.esprit.tic.timeforge.Service.SmartPlannerService;

import java.util.List;

@RestController
@RequestMapping("/api/calendar-events")
@RequiredArgsConstructor
public class CalendarIntegrationController {

    private final CalendarIntegrationService service;
    private final SmartPlannerService smartPlannerService;
    private final CalendarIntegrationRepository calendarIntegrationRepository;

    @GetMapping
    public List<CalendarIntegration> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarIntegration> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/auto-plan") // ➔ changement ici !
    public CalendarIntegration addEvent(@RequestBody CalendarIntegration event) {
        CalendarIntegration savedEvent = calendarIntegrationRepository.save(event);
        smartPlannerService.recalculatePlanAfterNewEvent(); // Nouvelle méthode
        return savedEvent;
    }

    @PostMapping // ➔ PAS de chemin ici, donc ça reste /api/calendar-events normal
    public CalendarIntegration create(@RequestBody CalendarIntegration event) {
        return service.create(event);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CalendarIntegration> update(@PathVariable Long id, @RequestBody CalendarIntegration event) {
        return service.update(id, event)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
