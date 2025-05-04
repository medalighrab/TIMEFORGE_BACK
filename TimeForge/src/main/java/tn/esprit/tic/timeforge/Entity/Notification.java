package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.tic.timeforge.Entity.Ennum.Status;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification  implements Serializable {
    @Id
    @GeneratedValue
    int id;
    String title;
    String message;
    String date;
    Status status;


    @ManyToOne
    HealthReminder HealthReminder;
    @ManyToOne
    StrategicReminder strategicreminder;
    @ManyToOne
    Interface interface1;
    @ManyToOne
    Deadline deadline;



}
