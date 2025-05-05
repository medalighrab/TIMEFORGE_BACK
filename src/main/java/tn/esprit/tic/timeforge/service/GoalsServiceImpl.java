package tn.esprit.tic.timeforge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.entity.Goals;
import tn.esprit.tic.timeforge.entity.Task;
import tn.esprit.tic.timeforge.repository.GoalsRepository;
import tn.esprit.tic.timeforge.repository.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalsServiceImpl implements GoalsService {

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
    public Goals addGoalAndAssignToTask(Goals newGoal, Long taskId) {
        goalsRepository.save(newGoal);
        Task task= taskRepository.findById(taskId).orElse(null);
        task.getGoals().add(newGoal);
        newGoal.setTask13(task);
        taskRepository.save(task);
        return newGoal;
    }
}
