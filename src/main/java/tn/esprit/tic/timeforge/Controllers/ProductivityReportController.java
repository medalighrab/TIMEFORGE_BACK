
package tn.esprit.tic.timeforge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.ProductivityReport;
import tn.esprit.tic.timeforge.Service.ProductivityAnalysisService;

import java.util.List;

@RestController
@RequestMapping("/api/productivity")
@RequiredArgsConstructor
@CrossOrigin
public class ProductivityReportController {

    private final ProductivityAnalysisService analysisService;

    @PostMapping("/analyze/{userId}")
    public ProductivityReport analyze(@PathVariable Long userId) {
        return analysisService.analyze(userId);
    }

    @GetMapping("/reports/{userId}")
    public List<ProductivityReport> getReports(@PathVariable Long userId) {
        return analysisService.getReports(userId);
    }
}
