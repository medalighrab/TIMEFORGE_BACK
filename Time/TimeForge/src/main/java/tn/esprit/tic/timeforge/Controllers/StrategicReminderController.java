package tn.esprit.tic.timeforge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.StrategicReminder;
import tn.esprit.tic.timeforge.Service.StrategicReminderService;

import java.util.List;

@RestController
@RequestMapping("/api/strategic-reminders")
@RequiredArgsConstructor
public class StrategicReminderController {

    @Autowired
    StrategicReminderService strategicReminderService;

    @PostMapping
    public ResponseEntity<StrategicReminder> addStrategicReminder(@RequestBody StrategicReminder strategicReminder) {
        return ResponseEntity.ok(strategicReminderService.addStrategicReminder(strategicReminder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StrategicReminder> updateStrategicReminder(@PathVariable Long id, @RequestBody StrategicReminder strategicReminder) {
        StrategicReminder updatedReminder = strategicReminderService.updateStrategicReminder(id, strategicReminder);
        return updatedReminder != null ? ResponseEntity.ok(updatedReminder) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrategicReminder(@PathVariable Long id) {
        strategicReminderService.deleteStrategicReminder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StrategicReminder> getStrategicReminderById(@PathVariable Long id) {
        StrategicReminder reminder = strategicReminderService.getStrategicReminderById(id);
        return reminder != null ? ResponseEntity.ok(reminder) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<StrategicReminder>> getAllStrategicReminders() {
        return ResponseEntity.ok(strategicReminderService.getAllStrategicReminders());
    }
}
