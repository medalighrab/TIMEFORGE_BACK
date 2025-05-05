
package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.ProductivityReport;
import tn.esprit.tic.timeforge.Entity.TimeTracker;
import tn.esprit.tic.timeforge.Repository.ProductivityReportRepository;
import tn.esprit.tic.timeforge.Repository.TimeTrackerRepository;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductivityAnalysisService {

    private final TimeTrackerRepository trackerRepo;
    private final ProductivityReportRepository reportRepo;

    public ProductivityReport analyze(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = today.with(DayOfWeek.SUNDAY);

        List<TimeTracker> sessions = trackerRepo.findAll().stream()
                .filter(t -> t.getUser().getIdUser().equals(userId))
                .filter(t -> !t.getCreationDate().isBefore(weekStart) && !t.getCreationDate().isAfter(weekEnd))
                .toList();

        int total = sessions.size();
        int completed = (int) sessions.stream().filter(TimeTracker::isCompleted).count();
        int failed = total - completed;
        double avgDuration = sessions.stream().mapToInt(TimeTracker::getEstimatedMinutes).average().orElse(0);

        Map<Integer, Long> hourCounts = sessions.stream()
                .filter(TimeTracker::isCompleted)
                .map(t -> t.getStartTime().getHour())
                .collect(Collectors.groupingBy(h -> h, Collectors.counting()));

        String bestHour = hourCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> e.getKey() + ":00")
                .orElse("Inconnu");

        String advice = (avgDuration > 90)
                ? "Essayez des sessions plus courtes (45–60 min)."
                : "Continuez sur ce rythme 👌";

        ProductivityReport report = new ProductivityReport(null, userId, weekStart, weekEnd,
                total, completed, failed, avgDuration, bestHour, advice);

        return reportRepo.save(report);
    }

    public List<ProductivityReport> getReports(Long userId) {
        return reportRepo.findByUserId(userId);
    }
}
