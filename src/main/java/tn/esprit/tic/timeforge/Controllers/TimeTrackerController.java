package tn.esprit.tic.timeforge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.TimeTracker;
import tn.esprit.tic.timeforge.Service.TimeTrackerService;

import java.util.List;

@RestController
@RequestMapping("/api/time-trackers")
@RequiredArgsConstructor
public class TimeTrackerController {

    private final TimeTrackerService service;

    @GetMapping
    public List<TimeTracker> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeTracker> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TimeTracker create(@RequestBody TimeTracker tracker) {
        return service.create(tracker);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeTracker> update(@PathVariable Long id, @RequestBody TimeTracker tracker) {
        return service.update(id, tracker)
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
    @PostMapping("/schedule")
    public ResponseEntity<TimeTracker> scheduleTracker(@RequestBody TimeTracker tracker) {
        return service.schedule(tracker)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

}
