package tn.esprit.tic.timeforge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.tic.timeforge.Entity.Project;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Service.ProjectEstimationService;
import tn.esprit.tic.timeforge.Service.ProjectServices;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectServices projectService;

    @Autowired
    private ProjectEstimationService estimationService;

    /**
     * Endpoint pour estimer la date de fin d'un projet.
     * Retourne un JSON avec la date estimée ou un message d'erreur.
     */
    @PostMapping("/{projectId}/estimate-end-date")
    public ResponseEntity<Map<String, Object>> estimateEndDate(@PathVariable Long projectId) {
        try {
            LocalDate estimatedEndDate = estimationService.estimateProjectEndDate(projectId);
            Map<String, Object> response = new HashMap<>();
            response.put("estimated_end_date", estimatedEndDate.toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Erreur si le projet n'existe pas
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Le projet avec l'ID " + projectId + " n'a pas été trouvé."));
        }
    }

    /**
     * Endpoint pour suggérer une solution pour le projet.
     * Retourne un JSON avec la solution ou un message d'erreur.
     */
    @GetMapping("/suggest-solution/{projectId}")
    public ResponseEntity<Map<String, String>> suggestSolution(@PathVariable Long projectId) {
        try {
            String solution = estimationService.suggestSolutionForProject(projectId);
            return ResponseEntity.ok(Map.of("solution", solution));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Le projet avec l'ID " + projectId + " n'a pas été trouvé."));
        }
    }

    /**
     * Endpoint pour appliquer la solution suggérée au projet.
     * - Si 'apply' = true : enregistre l'ancienne deadline, met à jour la nouvelle et renvoie un message JSON.
     * - Sinon : renvoie un message JSON indiquant que la solution n'a pas été appliquée.
     */
    @PostMapping("/apply-solution/{projectId}")
    public ResponseEntity<Map<String, String>> applySolution(@PathVariable Long projectId,
                                                             @RequestParam boolean apply) {
        try {
            if (apply) {
                estimationService.applySuggestedSolution(projectId);
                return ResponseEntity.ok(Map.of("message", "Solution appliquée automatiquement."));
            } else {
                return ResponseEntity.ok(Map.of("message", "Solution non appliquée (choix de l'utilisateur)."));
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            // Déterminer le statut HTTP en fonction de l'erreur
            HttpStatus status = (errorMessage != null && errorMessage.toLowerCase().contains("introuvable"))
                    ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                    .body(Map.of("message", errorMessage));
        }
    }

    // --- Endpoints CRUD standards pour les projets ---

    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @GetMapping("/list")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        return projectService.updateProject(id, projectDetails);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteProject(@PathVariable("id") Long id) {
        try {
            projectService.deleteProjectById(id);
            return ResponseEntity.ok(Map.of("message", "Project deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Project not found with ID: " + id));
        }
    }

    @GetMapping("/upcoming-deadlines")
    public List<Project> getProjectsWithUpcomingDeadlines() {
        return projectService.getProjectsWithUpcomingDeadlines();
    }

    @GetMapping("/by-project/{projectId}")
    public ResponseEntity<List<User>> getUsersByProject(@PathVariable Long projectId) {
        List<User> users = projectService.getUsersByProjectId(projectId);
        return ResponseEntity.ok(users);
    }


}
