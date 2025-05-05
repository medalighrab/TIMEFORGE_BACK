package tn.esprit.tic.timeforge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tic.timeforge.Entity.ProjectComment;

import java.util.List;

public interface ProjectCommentRepository   extends JpaRepository<ProjectComment, Long> {
    List<ProjectComment> findByProjectId(Long projectId);
    List<ProjectComment> findByProject_Projectid(Long projectId);

}
