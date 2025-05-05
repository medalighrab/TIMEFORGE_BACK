package tn.esprit.tic.timeforge.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.timeforge.entity.Ennum.RoleName;
import tn.esprit.tic.timeforge.entity.Ennum.StatusTask;
import tn.esprit.tic.timeforge.entity.Project;
import tn.esprit.tic.timeforge.entity.Role;
import tn.esprit.tic.timeforge.entity.Task;
import tn.esprit.tic.timeforge.entity.User;
import tn.esprit.tic.timeforge.repository.IprojetRepo;
import tn.esprit.tic.timeforge.repository.RoleRepository;
import tn.esprit.tic.timeforge.repository.TaskRepository;
import tn.esprit.tic.timeforge.repository.UserRepository;
import tn.esprit.tic.timeforge.dto.TaskDTO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class TaskService implements IntTaskService {
   @Autowired
    TaskRepository taskRepository;
    @Autowired
    private GamificationService gamificationService;

    @Autowired
    IprojetRepo iprojetRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> addTask(Task task, Long idprojet, Long employeeId, Long lead) {
        Project project = iprojetRepo.findById(idprojet).orElse(null);
        if (project == null) {
            return new ResponseEntity<>("Project not found", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findById(employeeId).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("Employee not found", HttpStatus.BAD_REQUEST);
        }
        User leaduser = userRepository.findById(lead).orElse(null);
        if (leaduser == null) {
            return new ResponseEntity<>("Team leader not found", HttpStatus.BAD_REQUEST);
        }
        LocalDate today = LocalDate.now();
        if (task.getDeadline().isBefore(today)) {
            return new ResponseEntity<>("Deadline cannot be in the past", HttpStatus.BAD_REQUEST);
        }
        if (task.getStartDate() != null && task.getStartDate().isBefore(today)) {
            return new ResponseEntity<>("Start date cannot be in the past", HttpStatus.BAD_REQUEST);
        }
        task.setProject(project);
        task.setEmployee11(user);
        task.setTeamleader(leaduser);

        Task savedTask = taskRepository.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }
    @Override
    public void deleteTask(Long id) {
          taskRepository.deleteById(id );
    }
    @Override
    public void deleteTaskbyemployee(Long id,Boolean etat) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
        task.setIsdeleted(etat);
        taskRepository.save(task);
    }
    @Override
    public List<Task> filterTasks(String name, String description, LocalDate startDate, LocalDate deadline, StatusTask status) {
        Specification<Task> spec = Specification.where(TaskSpecification.isNotDeleted())
                .and(TaskSpecification.hasNameLike(name))
                .and(TaskSpecification.hasDescriptionLike(description))
                .and(TaskSpecification.hasStartDateAfter(startDate))
                .and(TaskSpecification.hasDeadlineBefore(deadline))
                .and(TaskSpecification.hasStatus(status));

        // Recherche des tâches correspondant à la spécification
        return taskRepository.findAll(spec);
    }



    @Override
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setName(updatedTask.getName());
                    existingTask.setDescription(updatedTask.getDescription());

                    existingTask.setStatus(updatedTask.getStatus());
                    return taskRepository.save(existingTask);
                })
                .orElseThrow(() -> new EntityNotFoundException("Task with ID " + id + " not found"));
    }
    @Override
    @Transactional
    public String updateTaskStatus(Long id, StatusTask updatedStatus) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return "❌ Task not found.";
        }

        StatusTask currentStatus = task.getStatus();

        if (currentStatus == StatusTask.DONE && updatedStatus == StatusTask.TODO) {
            return "⛔ Cannot change status from DONE back to TODO.";
        }

        StringBuilder message = new StringBuilder();
        message.append("✅ Status changed from ").append(currentStatus)
                .append(" to ").append(updatedStatus).append(".\n");

        task.setStatus(updatedStatus);
        if (updatedStatus == StatusTask.DONE ) {
            LocalDate today = LocalDate.now();
            if (task.getDeadline() != null && task.getDeadline().isBefore(today)) {
                task.setStatus(StatusTask.DONE_RETARD);
               gamificationService.rewardUser(task.getEmployee11(), 10);
            } else {
               gamificationService.rewardUser(task.getEmployee11(), 30);
            }
          

            if (task.getStartDate() != null) {
                long actualDays = ChronoUnit.DAYS.between(task.getStartDate(), today);
                int actualHours = (int) (actualDays * 8); // réel

                Integer durationInDays = (int) task.getDurationInDays();
                int estimatedHours = (durationInDays != null) ? durationInDays * 8 : 0;


                message.append("📊 Durée réelle : ").append(actualHours)
                        .append("h. Estimation initiale : ").append(estimatedHours).append("h.\n");

                if (estimatedHours > 0) {
                    if (actualHours > estimatedHours) {
                        message.append("⚠️ Estimation dépassée de ")
                                .append(actualHours - estimatedHours).append(" heures.\n");
                    } else {
                        message.append("🕒 Tâche réalisée dans le temps estimé.\n");
                    }
                } else {
                    message.append("⚠️ Aucune estimation initiale définie.\n");
                }

                task.setEstimatedHours(actualHours);
            }
        }

        taskRepository.save(task);
        return message.toString();
    }





    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with ID " + id + " not found"));

    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    @Override
    public List<Task> getAllTasksbyUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return taskRepository.findAllByEmployee11AndIsdeletedIsFalse(user);
    }
    @Override
    public List<Task> getAllTasksbyUserHistorique(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return taskRepository.findAllByEmployee11AndIsdeletedIsTrue(user);
    }
    @Override
    public Set<User> getAllUSER() {
        Role role = roleRepository.findByName(RoleName.ROLE_EMPLOYEE).get();
        return userRepository.findAllByRoles(role);
    }
    @Override
    public List<Project> getall() {
        return iprojetRepo.findAll();
    }
    @Override
    public List<Project> getallprojetbyidLead(Long id) {
        System.out.println(id);
        User user = userRepository.findById(id).orElse(null);
        return iprojetRepo.findAllByTeamlead(user);
    }
    @Override
    public Map<String, Object> getTaskStatistics(Long idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        List<Task> tasks = taskRepository.findAllByEmployee11AndIsdeletedIsFalse(user);

        long totalTasks = tasks.size();
        long todoTasks = tasks.stream().filter(t -> t.getStatus() == StatusTask.TODO).count();
        long inProgressTasks = tasks.stream().filter(t -> t.getStatus() == StatusTask.IN_PROGRESS).count();
        long doneTasks = tasks.stream().filter(t -> t.getStatus() == StatusTask.DONE).count();
        long lateTasks = tasks.stream().filter(t -> t.getStatus() == StatusTask.DONE_RETARD).count();

        double avgEstimatedHours = tasks.stream()
                .filter(t -> (Integer)t.getEstimatedHours() != null)
                .mapToInt(Task::getEstimatedHours)
                .average().orElse(0.0);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTasks", totalTasks);
        stats.put("todoTasks", todoTasks);
        stats.put("inProgressTasks", inProgressTasks);
        stats.put("doneTasks", doneTasks);
        stats.put("lateTasks", lateTasks);
        stats.put("averageEstimatedHours", avgEstimatedHours);

        return stats;
    }
    @Override
    public Map<String, Object> getTaskDurations(Long idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Task> tasks = taskRepository.findAllByEmployee11AndIsdeletedIsFalse(user);

        double totalEstimatedDuration = tasks.stream()
                .filter(t ->(Long) t.getDurationInDays() != null)
                .mapToDouble(t -> t.getDurationInDays() * 8)
                .sum();

        double totalActualDuration = tasks.stream()
                .filter(t -> (Integer) t.getEstimatedHours() != null)
                .mapToDouble(Task::getEstimatedHours)
                .sum();

        double totalDelay = totalEstimatedDuration - totalActualDuration;

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalEstimatedDuration", totalEstimatedDuration);
        stats.put("totalActualDuration", totalActualDuration);
        stats.put("totalDelay", totalDelay);

        return stats;
    }


}
