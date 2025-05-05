package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.Goals;
import tn.esprit.tic.timeforge.Entity.Task;
import tn.esprit.tic.timeforge.Repository.GoalsRepository;
import tn.esprit.tic.timeforge.Repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class  GoalsServiceImpl implements GoalsService {

    @Autowired
    GoalsRepository goalsRepository;

    @Autowired
    TaskRepository taskRepository;

    @Override
    public Goals addGoal(Goals goal) {
        return goalsRepository.save(goal);
    }

//    @Override
//    public Goals updateGoal(Long id, Goals goal) {
//        Optional<Goals> existingGoal = goalsRepository.findById(id);
//        if (existingGoal.isPresent()) {
//            Goals updatedGoal = existingGoal.get();
//            updatedGoal.setTitle(goal.getTitle());
//            updatedGoal.setDescription(goal.getDescription());
//            return goalsRepository.save(updatedGoal);
//        }
//        return null;
//    }

    @Override
    public void deleteGoal(Long id) {
        goalsRepository.deleteById(id);
    }

    @Override
    public Goals getGoalById(Long id) {
        return goalsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Goals> getAllGoals() {
        return goalsRepository.findAll();
    }

    @Override
    public Goals addGoalAndAssignToTask(Goals newGoal, long taskId) {
        goalsRepository.save(newGoal);
        Task task = taskRepository.findById(taskId).orElse(null);
        task.getGoals().add(newGoal);
        newGoal.setTask13(task);
        taskRepository.save(task);
        return newGoal;
    }

    @Override
    public List<Goals> getCompletedGoals() {
        return goalsRepository.findByStatusGoal("COMPLETED");  // Statut terminé
    }
    @Override
    public double calculateUserScore(Long userId) {
        List<Goals> userGoals = goalsRepository.findByUser_IdUser(userId); // Utilisation correcte
        int totalGoals = userGoals.size();
        int completedGoals = 0;

        // Comptabilise les objectifs terminés
        for (Goals goal : userGoals) {
            if ("COMPLETED".equals(goal.getStatusGoal().name())) {  // Compare avec le nom de l'enum
                completedGoals++;
            }
        }

        // Retourne le score en pourcentage
        return totalGoals > 0 ? ((double) completedGoals / totalGoals) * 100 : 0.0;
    }


}
