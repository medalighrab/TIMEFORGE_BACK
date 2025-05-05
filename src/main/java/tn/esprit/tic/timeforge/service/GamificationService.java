package tn.esprit.tic.timeforge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.entity.GamificationProfile;
import tn.esprit.tic.timeforge.entity.User;
import tn.esprit.tic.timeforge.repository.GamificationProfileRepository;
import tn.esprit.tic.timeforge.repository.UserRepository;

import java.util.List;

@Service
public class GamificationService {

    @Autowired
    private GamificationProfileRepository gamificationProfileRepository;
    @Autowired
    private UserRepository userRepository;
    public void rewardUser(User user, int points) {
        GamificationProfile profile = gamificationProfileRepository.findByUser(user)
                .orElseGet(() -> {
                    GamificationProfile newProfile = new GamificationProfile();
                    newProfile.setUser(user);
                    newProfile.setXp(0);
                    newProfile.setLevel(1);
                    newProfile.setCompletedTasks(0);
                    return newProfile;
                });
        int currentXp = profile.getXp();
        int newXp = currentXp + points;
        profile.setXp(newXp);
        int newLevel = (newXp / 100) + 1;
        profile.setLevel(newLevel);
        if (points > 0) {
            profile.setCompletedTasks(profile.getCompletedTasks() + 1);
        }
        gamificationProfileRepository.save(profile);
    }


    public List<GamificationProfile> getTopGamers() {
        return gamificationProfileRepository.findTop5ByOrderByXpDesc();
    }

    public GamificationProfile GetBadgeByUser(Long id) {
        User user = userRepository.findById(id).get();
        return gamificationProfileRepository.findByUser(user).get();
    }

}
