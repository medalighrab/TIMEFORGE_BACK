package tn.esprit.tic.timeforge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeTracker  {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    Task task;
}
