package tn.esprit.tic.timeforge.Service;

import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.TimeTracker;

import java.time.LocalTime;

@Service
public class SessionRiskService {

    public double predictRisk(TimeTracker session) {
        double risk = 0.0;

        // ⏱️ Durée excessive
        if (session.getEstimatedMinutes() > 90) {
            risk += 0.3;
        }

        // 🌙 Heure de début tardive
        if (session.getStartTime() != null && session.getStartTime().isAfter(LocalTime.of(18, 0))) {
            risk += 0.4;
        }

        // 🔻 Priorité faible
        if (session.getPriority() != null && session.getPriority().equalsIgnoreCase("basse")) {
            risk += 0.3;
        }

        // 🔐 Limiter le risque entre 0 et 1
        return Math.min(risk, 1.0);
    }
}
