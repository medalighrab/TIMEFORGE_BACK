package tn.esprit.tic.timeforge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.Goals;

import java.util.List;

@Repository
public interface GoalsRepository extends JpaRepository<Goals, Long> {
    List<Goals> findGoalsByTask13_Id(Long taskId);

    // Trouver les objectifs actifs chroniquement
    List<Goals> findByChronicActiveTrue();

    // Trouver les objectifs en fonction de leur statut
    List<Goals> findByStatusGoal(String statusGoal);

    // Trouver les objectifs associés à un utilisateur particulier
    List<Goals> findByUser_IdUser(Long userId);

    // Ajouter d'autres méthodes personnalisées si nécessaire pour des cas particuliers
    List<Goals> findByTitleContainingIgnoreCase(String title);


}

