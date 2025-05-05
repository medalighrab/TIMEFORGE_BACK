package tn.esprit.tic.timeforge.Controllers;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.TimeTracker;
import tn.esprit.tic.timeforge.Service.SessionRiskService;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SessionAIController {

    private final SessionRiskService sessionRiskService;

    @PostMapping("/session-risk")
    public double getRisk(@RequestBody TimeTracker tracker) {
        return sessionRiskService.predictRisk(tracker);
    }
}
