package tn.esprit.tic.timeforge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tic.timeforge.entity.GamificationProfile;
import tn.esprit.tic.timeforge.service.GamificationService;

import java.util.List;

@RestController
@RequestMapping("/api/gamification")
public class GamificationController {

    @Autowired
    private GamificationService gamificationService;

    @GetMapping("/leaderboard")
    public List<GamificationProfile> getLeaderboard() {
        return gamificationService.getTopGamers();
    }

    @GetMapping("/getbadge/{id}")
    public GamificationProfile getbadge(@PathVariable Long id) {
        return gamificationService.GetBadgeByUser(id);
    }

}

