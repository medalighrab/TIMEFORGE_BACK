package tn.esprit.tic.timeforge.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.Ennum.StatusTask;
import tn.esprit.tic.timeforge.Entity.Project;
import tn.esprit.tic.timeforge.Entity.Task;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Service.IntTaskService;
import tn.esprit.tic.timeforge.Service.TaskService;

import java.util.List;
import java.util.Set;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("tasks")
public class TaskController {
    @Autowired
    IntTaskService intTaskService;

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task, @RequestParam Long id,
                                        @RequestParam(value = "employeeid") Long employeeid,
                                        @RequestParam(value = "lead") Long lead) {
        return ResponseEntity.status(201).body(intTaskService.addTask(task, id, employeeid, lead));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        intTaskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tasksbyuser/{id}")
    public List<Task> getalltaskbyuser(@PathVariable Long id) {
        return intTaskService.getAllTasksbyUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task task = intTaskService.updateTask(id, updatedTask);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<String> updateTaskStatus(@PathVariable Long id, @RequestBody Task updatedTask) {
        return new ResponseEntity<>(intTaskService.updateTaskStatus(id, updatedTask.getStatus()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = intTaskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = intTaskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/allproject")
    public ResponseEntity<List<Project>> getAll() {
        List<Project> p = intTaskService.getall();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<Set<User>> getAllUser() {
        Set<User> p = intTaskService.getAllUSER();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    // New endpoints for the added service methods

    @PostMapping("/add-with-assign/{idUser}/{idProject}")
    public ResponseEntity<Task> addAndAssignTask(
            @RequestBody Task task,
            @PathVariable Long idUser,
            @PathVariable Long idProject) {

        System.out.println(">>> Requête reçue sans blocage de sécurité"); // ✅ log test
        Task newTask = intTaskService.ajouterEtAffecterTache(task, idUser, idProject);
        return ResponseEntity.ok(newTask);
    }


    @PutMapping("/reassign/{idTask}/user/{idUser}/project/{idProjet}")
    public ResponseEntity<Task> reassignTaskToUserAndProject(@PathVariable Long idTask,
                                                             @PathVariable Long idUser,
                                                             @PathVariable Long idProjet) {
        Task task = intTaskService.ReassignTasksAUserEtProjet(idTask, idUser, idProjet);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/backlog")
    public ResponseEntity<List<Task>> getBacklogTasks() {
        List<Task> tasks = intTaskService.listTaskBLlog();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/todo")
    public ResponseEntity<List<Task>> getTodoTasks() {
        List<Task> tasks = intTaskService.listTasktodo();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/inprogress")
    public ResponseEntity<List<Task>> getInProgressTasks() {
        List<Task> tasks = intTaskService.listTaskinprogress();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/done")
    public ResponseEntity<List<Task>> getDoneTasks() {
        List<Task> tasks = intTaskService.listTaskdone();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }





        @GetMapping("/project/{projectId}")
        public ResponseEntity<List<Task>> getTasksByProjectId(@PathVariable Long projectId) {
            List<Task> tasks = intTaskService.getTasksByProjectId(projectId);
            return ResponseEntity.ok(tasks);
        }

//    // Méthode exposée via un endpoint GET pour obtenir les tâches par status et projet
//    @GetMapping("/status-and-project")
//    @ResponseBody
//    public List<Task> listTaskByStatusAndProject(
//            @RequestParam("projectId") Long projectId,
//            @RequestParam("status") StatusTask status) {
//
//        // Appel de la méthode du service
//        return intTaskService.listTaskByStatusAndProject(projectId, status);
//    }
    }

