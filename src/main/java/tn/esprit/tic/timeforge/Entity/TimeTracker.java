package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import tn.esprit.tic.timeforge.Entity.TimeManagementTechniques;
import tn.esprit.tic.timeforge.Entity.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeTracker implements Serializable {

    @Id
    @GeneratedValue
    long id;

    String title;
    String description;
    boolean completed;
    int estimatedMinutes;

    @ManyToOne
    User user;



    @ManyToOne
    TimeManagementTechniques technique;

    LocalTime startTime;

    // ✅ Champ obligatoire pour le rapport
    LocalDate creationDate;
    private String priority; // Exemples : "Haute", "Moyenne", "Basse"
    public int getStartHour() {
        return (startTime != null) ? startTime.getHour() : 0;
    }


}
