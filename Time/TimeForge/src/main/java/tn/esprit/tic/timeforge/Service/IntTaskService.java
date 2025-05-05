package tn.esprit.tic.timeforge.Service;


import tn.esprit.tic.timeforge.Entity.Ennum.StatusTask;
import tn.esprit.tic.timeforge.Entity.Project;
import tn.esprit.tic.timeforge.Entity.Task;
import tn.esprit.tic.timeforge.Entity.User;

import java.util.List;
import java.util.Set;

public interface IntTaskService  {
    public Task addTask(Task task ,Long idprojet,Long employeeId,Long lead);
//    public List<Project> getallprojetbyidLead(Long id);
    public String updateTaskStatus(Long id, StatusTask updatedTask);
    public List<Task> getAllTasksbyUser(Long id);
    void deleteTask (Long id  );
    public List<Project> getall();
    public Set<User> getAllUSER();
     Task updateTask(Long id, Task updatedTask);
    public Task ajouterEtAffecterTache(Task task, Long idUser, Long idProjet);

     Task AffecteTasksAUserEtProjet(Long idTask,Long idUser,Long idProjet);
     Task ReassignTasksAUserEtProjet(Long idTask,Long idUser,Long idProjet);

    List <Task> listTaskBLlog();
    List <Task> listTasktodo();
    List <Task> listTaskinprogress();
    List <Task> listTaskdone();
//    public long getTaskCountByProject(Long projectId);
//    public long getTaskCountByUser(Long userId);

    Task getTaskById(Long id);
    List <Task> getAllTasks();
    public List<Task> getTasksByProjectId(Long projectId);
}
