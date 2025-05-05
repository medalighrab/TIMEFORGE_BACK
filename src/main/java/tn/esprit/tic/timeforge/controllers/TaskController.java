package tn.esprit.tic.timeforge.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.entity.Ennum.StatusTask;
import tn.esprit.tic.timeforge.entity.Project;
import tn.esprit.tic.timeforge.entity.Task;
import tn.esprit.tic.timeforge.entity.User;
import tn.esprit.tic.timeforge.service.IntTaskService;
import tn.esprit.tic.timeforge.dto.TaskDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;


@AllArgsConstructor
@RestController
@RequestMapping("tasks")
public class TaskController {
    @Autowired
    IntTaskService intTaskService;

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task task,@RequestParam Long id,@RequestParam(value = "employeeid") Long employeeid,@RequestParam(value = "lead") Long lead){
        return  intTaskService.addTask(task,id,employeeid,lead);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        intTaskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/archive/{id}/{etat}")
    public ResponseEntity<?> archive(@PathVariable Long id,@PathVariable Boolean etat) {
        intTaskService.deleteTaskbyemployee(id,etat);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/tasksbyuser/{id}")
    public List<Task> getalltaskbyuser(@PathVariable Long id) {

        return intTaskService.getAllTasksbyUser(id);
    }
    @GetMapping("/taskshis/{id}")
    public List<Task> getalltaskhisto(@PathVariable Long id) {

        return intTaskService.getAllTasksbyUserHistorique(id);
    }
    @GetMapping("/stat/{id}")
    public Map<String, Object> statofuser(@PathVariable Long id) {

        return intTaskService.getTaskStatistics(id);
    }
    @GetMapping("/statestimation/{id}")
    public Map<String, Object> statestimation(@PathVariable Long id) {

        return intTaskService.getTaskDurations(id);
    }
    @GetMapping("/projetsbyuser/{id}")
    public List<Project> getallprojetbyuser(@PathVariable Long id) {
        return intTaskService.getallprojetbyidLead(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {

            Task task = intTaskService.updateTask(id, updatedTask);
            return new ResponseEntity<>(task, HttpStatus.OK);

    }
    @GetMapping("/filter")
    public ResponseEntity<List<Task>> filterTasks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline,
            @RequestParam(required = false) StatusTask status) {

        List<Task> tasks = intTaskService.filterTasks(name, description, startDate, deadline, status);
        return ResponseEntity.ok(tasks);
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
}

