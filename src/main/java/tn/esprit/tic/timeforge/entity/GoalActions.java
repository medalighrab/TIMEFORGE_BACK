package tn.esprit.tic.timeforge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.tic.timeforge.entity.Ennum.StatusGoal;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoalActions  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int goalid;
    String action;
    StatusGoal statusGoal;
    Date createdAt;
    @ManyToOne
    @JsonIgnore
    SubGoal subgoal;
}
