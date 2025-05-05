package tn.esprit.tic.timeforge.Service.IMP;

import org.springframework.http.ResponseEntity;
import tn.esprit.tic.timeforge.Entity.Project;

import java.util.List;

public interface IProjectService {
    Project createProject(Project project);
    List<Project> getAllProjects();
    ResponseEntity<Project> getProjectById(Long id);
    ResponseEntity<Project> updateProject(Long id, Project projectDetails);
    ResponseEntity<String> deleteProject(Long id);

    void sendNotification(Project project) ;
    public void checkProjectDeadlines();


}
