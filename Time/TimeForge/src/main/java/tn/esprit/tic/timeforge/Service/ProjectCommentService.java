package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.Project;
import tn.esprit.tic.timeforge.Entity.ProjectComment;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Repository.ProjectCommentRepository;
import tn.esprit.tic.timeforge.Repository.ProjectRepository;
import tn.esprit.tic.timeforge.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service

public class ProjectCommentService {

    @Autowired
    private ProjectCommentRepository commentRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    // Affecter un commentaire à un projet
    // Affecter un commentaire à un projet
    public ProjectComment affecterCommentAProject(Long projectId, Long userId, String commentText) {
        ProjectComment comment = new ProjectComment();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));
        comment.setProject(project); // 🔥 on met le vrai objet Project ici

        comment.setComment(commentText);
        comment.setCreatedAt(LocalDateTime.now());

        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            comment.setUserComment(user);
        }

        return commentRepository.save(comment);
    }


    // Désaffecter (supprimer) un commentaire
    public void desaffecterCommentAProject(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new RuntimeException("Commentaire introuvable");
        }
        commentRepository.deleteById(commentId);
    }

    // Lister les commentaires d’un projet
    public List<ProjectComment> listeCommentsParProject(Long projectId) {
        return commentRepository.findByProject_Projectid(projectId);
    }
}