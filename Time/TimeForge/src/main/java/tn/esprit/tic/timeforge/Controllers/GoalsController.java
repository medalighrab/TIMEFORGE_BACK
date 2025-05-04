package tn.esprit.tic.timeforge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.Goals;
import tn.esprit.tic.timeforge.Entity.Task;
import tn.esprit.tic.timeforge.Repository.GoalsRepository;
import tn.esprit.tic.timeforge.Service.GoalsService;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor  // Lombok générera automatiquement un constructeur avec l'injection des dépendances
public class GoalsController {
    @Autowired
    GoalsService  goalsService;
    @Autowired
    GoalsRepository  goalsRepository;



    @PostMapping("add/{TaskId}")
    public Goals ajout(@RequestBody Goals goals , @PathVariable long TaskId ){
        return goalsService.addGoalAndAssignToTask(goals,TaskId);

    }

    @PostMapping("rayen")
    public Goals ajouttt(@RequestBody Goals goals  ){
        return goalsRepository.save(goals);

    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Goals> updateGoal(@PathVariable Long id, @RequestBody Goals goal) {
//        Goals updatedGoal = goalsService.updateGoal(id, goal);
//        return updatedGoal != null ? ResponseEntity.ok(updatedGoal) : ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalsService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goals> getGoalById(@PathVariable Long id) {
        Goals goal = goalsService.getGoalById(id);
        return goal != null ? ResponseEntity.ok(goal) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Goals>> getAllGoals() {
        return ResponseEntity.ok(goalsService.getAllGoals());
    }
}
