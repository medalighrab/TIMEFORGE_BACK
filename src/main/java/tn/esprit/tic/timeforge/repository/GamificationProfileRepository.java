package tn.esprit.tic.timeforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tic.timeforge.entity.GamificationProfile;
import tn.esprit.tic.timeforge.entity.User;

import java.util.List;
import java.util.Optional;

public interface GamificationProfileRepository extends JpaRepository<GamificationProfile, Long> {
    Optional<GamificationProfile> findByUser(User user);
    List<GamificationProfile> findTop5ByOrderByXpDesc();
}

