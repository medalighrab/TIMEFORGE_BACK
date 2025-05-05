package tn.esprit.tic.timeforge.service;


import org.springframework.http.ResponseEntity;
import tn.esprit.tic.timeforge.entity.Ennum.StatusTask;
import tn.esprit.tic.timeforge.entity.Project;
import tn.esprit.tic.timeforge.entity.Task;
import tn.esprit.tic.timeforge.entity.User;
import tn.esprit.tic.timeforge.dto.TaskDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IntTaskService  {
    public ResponseEntity<?> addTask(Task task , Long idprojet, Long employeeId, Long lead);
    public void deleteTaskbyemployee(Long id,Boolean etat);
    public List<Task> filterTasks(String name, String description, LocalDate startDate, LocalDate deadline, StatusTask status);
    public List<Task> getAllTasksbyUserHistorique(Long id);
    public Map<String, Object> getTaskStatistics(Long idUser);
    public Map<String, Object> getTaskDurations(Long idUser);
    public List<Project> getallprojetbyidLead(Long id);
    public String updateTaskStatus(Long id, StatusTask updatedTask);
    public List<Task> getAllTasksbyUser(Long id);
    void deleteTask (Long id  );
    public List<Project> getall();
    public Set<User> getAllUSER();
     Task updateTask(Long id, Task updatedTask);


    Task getTaskById(Long id);
    List <Task> getAllTasks();

}
