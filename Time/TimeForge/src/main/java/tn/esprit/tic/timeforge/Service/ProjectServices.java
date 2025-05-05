package tn.esprit.tic.timeforge.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.Ennum.StatusProject;
import tn.esprit.tic.timeforge.Entity.Project;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Repository.ProjectRepository;
import tn.esprit.tic.timeforge.Repository.TaskRepository;
import tn.esprit.tic.timeforge.Repository.UserRepository;
import tn.esprit.tic.timeforge.Service.IMP.IProjectService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServices implements IProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public ResponseEntity<Project> getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public void deleteProjectById(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(id);
    }
    @Override
    public ResponseEntity<Project> updateProject(Long id, Project projectDetails) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setName(projectDetails.getName());
            project.setDescription(projectDetails.getDescription());
            project.setDeadline(projectDetails.getDeadline());


            Project updatedProject = projectRepository.save(project);
            return ResponseEntity.ok(updatedProject);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return ResponseEntity.ok("Project deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    public List<Project> getProjectsWithUpcomingDeadlines() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        Date startDate = java.sql.Date.valueOf(today);
        Date endDate = java.sql.Date.valueOf(nextWeek);

        return projectRepository.findByDeadlineBetween(startDate, endDate);
    }

    ProjectServices projectService;



    @Scheduled(cron = "*/15 * * * * ?") // toutes les 15 secondes
    public void checkProjectDeadlines() {
        List<Project> upcomingProjects = getProjectsWithUpcomingDeadlines();

        for (Project project : upcomingProjects) {
            // Met à jour l'attribut upcomingDeadline de chaque projet
            if (!project.isUpcomingDeadline()) {
                project.setUpcomingDeadline(true);
                projectRepository.save(project);
                System.out.println("Le projet " + project.getName() + " a une deadline proche.");
            }

            sendNotification(project);
        }
    }public void sendNotification(Project project) {
        // Implémentez la logique d'envoi de notification (par exemple, envoi d'un e-mail)
            System.out.println("Notification envoyée chaque 15 secondes pour le projet  : " + project.getName());
    }






    // Cette méthode récupère tous les projets avec un statut IN_PROGRESS
    public List<Project> getInProgressProjects() {
        return projectRepository.findByStatus(StatusProject.IN_PROGRESS);
    }

    // Fonction programmée qui s'exécute toutes les 15 secondes
    @Scheduled(cron = "*/15 * * * * ?") // Toutes les 15 secondes
    public void checkInProgressProjects() {
        List<Project> inProgressProjects = getInProgressProjects(); // Appel direct
        for (Project project : inProgressProjects) {
            sendNotification2(project);
        }
    }
    public void sendNotification2(Project project) {
        // Logique d'envoi de notification (par exemple, envoi d'un email)
        System.out.println("Notification envoyée pour le projet en cours : " + project.getName());
    }



    public List<User> getUsersByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        return taskRepository.findByProject(project).stream()
                .map(task -> task.getEmployee11()) // ou .getTeamleader() si tu veux les leads
                .distinct()
                .collect(Collectors.toList());
    }

    public void markNotificationAsRead(Long notificationId) {
        System.out.println("Notification " + notificationId + " marquée comme lue.");

    }

    public List<String> getProjectNotifications() {
        List<Project> projectsWithUpcomingDeadlines = getProjectsWithUpcomingDeadlines();
        List<String> notifications = new ArrayList<>();
        for (Project project : projectsWithUpcomingDeadlines) {
            notifications.add("Deadline approchant pour le projet : " + project.getName());
        }
        return notifications;
    }
}


