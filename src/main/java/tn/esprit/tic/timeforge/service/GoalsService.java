package tn.esprit.tic.timeforge.service;

import tn.esprit.tic.timeforge.entity.Goals;

import java.util.List;

public interface GoalsService {
    Goals addGoal(Goals goal);

//    Goals updateGoal(Long id, Goals goal);

    void deleteGoal(Long id);

    Goals getGoalById(Long id);

    List<Goals> getAllGoals();

   Goals addGoalAndAssignToTask(Goals goal, Long taskId);
}
