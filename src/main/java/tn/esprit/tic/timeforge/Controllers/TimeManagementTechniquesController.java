package tn.esprit.tic.timeforge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.TimeManagementTechniques;
import tn.esprit.tic.timeforge.Service.TimeManagementTechniquesService;

import java.util.List;

@RestController
@RequestMapping("/api/time-techniques")
@RequiredArgsConstructor
public class TimeManagementTechniquesController {

    private final TimeManagementTechniquesService service;

    @GetMapping
    public List<TimeManagementTechniques> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeManagementTechniques> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TimeManagementTechniques create(@RequestBody TimeManagementTechniques technique) {
        return service.create(technique);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeManagementTechniques> update(@PathVariable Long id, @RequestBody TimeManagementTechniques technique) {
        return service.update(id, technique)
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
