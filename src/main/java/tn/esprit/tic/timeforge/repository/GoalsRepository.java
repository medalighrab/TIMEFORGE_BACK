package tn.esprit.tic.timeforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.entity.Goals;
@Repository
public interface GoalsRepository extends JpaRepository<Goals, Long> {
}
