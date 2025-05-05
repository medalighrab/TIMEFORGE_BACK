package tn.esprit.tic.timeforge.Controllers;

import tn.esprit.tic.timeforge.Service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/recommend")
    public String recommend(@RequestBody Map<String, String> payload) {
        String text = payload.get("text");
        return recommendationService.getRecommendation(text);
    }
}