package tn.esprit.tic.timeforge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.Project;
import tn.esprit.tic.timeforge.Service.ProjectServices;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private ProjectServices projectServices;


    @GetMapping("/project")
    public ResponseEntity<List<String>> getProjectNotificationss() {
        List<String> notifications = projectServices.getProjectNotifications();
        return ResponseEntity.ok(notifications);
    }


    // Endpoint pour récupérer les notifications de projets
    @GetMapping("/get-project-notifications")
    public ResponseEntity<List<Project>> getProjectNotifications() {
        // Vous pouvez filtrer les projets qui ont des notifications ou des échéances proches
        List<Project> projectsWithNotifications = projectServices.getProjectsWithUpcomingDeadlines(); // Par exemple
        return ResponseEntity.ok(projectsWithNotifications);
    }

    // Endpoint pour marquer une notification comme lue
    @PostMapping("/mark-as-read")
    public ResponseEntity<String> markNotificationAsRead(@RequestBody Long notificationId) {


        projectServices.markNotificationAsRead(notificationId);  // Implémentez cette méthode si nécessaire
        return ResponseEntity.ok("Notification marquée comme lue.");
    }

    // Optionnel : Endpoint pour récupérer les notifications non lues
    @GetMapping("/get-unread-notifications")
    public ResponseEntity<List<Project>> getUnreadNotifications() {
        // Retourne les notifications non lues, ou vous pouvez définir des critères ici.
        List<Project> unreadProjects = projectServices.getInProgressProjects(); // Par exemple
        return ResponseEntity.ok(unreadProjects);
    }
}

