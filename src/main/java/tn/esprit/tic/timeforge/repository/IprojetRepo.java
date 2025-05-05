package tn.esprit.tic.timeforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tic.timeforge.entity.Project;
import tn.esprit.tic.timeforge.entity.User;

import java.util.List;

public interface IprojetRepo extends JpaRepository<Project,Long> {

    List<Project> findAllByTeamlead(User teamlead);
}
