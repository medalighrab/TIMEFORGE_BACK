package tn.esprit.tic.timeforge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.ProjectComment;
import tn.esprit.tic.timeforge.Service.ProjectCommentService;
import tn.esprit.tic.timeforge.dto.ProjectCommentDTO;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class ProjectCommentController {
    private final ProjectCommentService commentService;




    @PostMapping("/affecter/{projectId}/{userId}")

    public ResponseEntity<ProjectComment> addCommentToProject(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @RequestParam String commentText) {
        ProjectComment comment = commentService.affecterCommentAProject(projectId, userId, commentText);
        return ResponseEntity.ok(comment);
    }
    @DeleteMapping("/delete/{id}")
    public void desaffecterComment(@PathVariable Long id) {
        commentService.desaffecterCommentAProject(id);
    }

    @GetMapping("/commments_for_Project/{projectId}")
    public List<ProjectCommentDTO> getCommentsParProjet(@PathVariable Long projectId) {
        return commentService.listeCommentsParProject(projectId).stream()
                .map(c -> new ProjectCommentDTO(
                        c.getId(),
                        c.getComment(),
                        c.getCreatedAt().toString(),
                        c.getUserComment() != null ? c.getUserComment().getUsername() : null,
                        c.getUserComment() != null ? c.getUserComment().getName() : null
                ))
                .toList();
    }

}