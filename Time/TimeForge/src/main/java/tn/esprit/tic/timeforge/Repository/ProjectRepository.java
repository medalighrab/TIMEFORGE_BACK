package tn.esprit.tic.timeforge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.Ennum.StatusProject;
import tn.esprit.tic.timeforge.Entity.Project;
import tn.esprit.tic.timeforge.Entity.ProjectComment;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByDeadlineBetween(Date start, Date end);
    List<Project> findByStatus(StatusProject status);
    Project getProjectByProjectid(Long projectid); // Use the field 'projectid'

}
