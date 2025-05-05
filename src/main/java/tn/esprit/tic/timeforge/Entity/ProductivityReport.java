
package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductivityReport {
    @Id @GeneratedValue
    Long id;
    Long userId;
    LocalDate weekStart;
    LocalDate weekEnd;
    int totalSessions;
    int completedSessions;
    int failedSessions;
    double averageDuration;
    String bestHour;
    String aiRecommendation;
}
