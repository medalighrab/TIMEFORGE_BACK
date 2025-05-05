package tn.esprit.tic.timeforge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tic.timeforge.Service.SmartPlannerService;
import tn.esprit.tic.timeforge.dto.SessionPlan;

import java.util.List;

@RestController
@RequestMapping("/api/smart-planner")
@RequiredArgsConstructor
public class SmartPlannerController {

    private final SmartPlannerService smartPlannerService;

    @GetMapping("/generate")
    public List<SessionPlan> generatePlan() {
        return smartPlannerService.generateSmartPlan();
    }
}
