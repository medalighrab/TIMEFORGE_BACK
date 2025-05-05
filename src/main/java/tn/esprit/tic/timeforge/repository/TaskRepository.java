package tn.esprit.tic.timeforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.entity.Ennum.StatusTask;
import tn.esprit.tic.timeforge.entity.Task;
import tn.esprit.tic.timeforge.entity.User;
import tn.esprit.tic.timeforge.dto.TaskDTO;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    List<Task> findAllByEmployee11AndIsdeletedIsFalse(User user);
    List<Task> findAllByEmployee11AndIsdeletedIsTrue(User user);


    List<Task> findByEmployee11(User user);

    List<Task> findByDeadline(LocalDate deadline);
}
