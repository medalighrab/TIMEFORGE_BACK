package tn.esprit.tic.timeforge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubGoal  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    int goalid;
    String title;
    String description;
    Date deadline;
    String progress;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="subgoal")
    @JsonIgnore
    private Set<GoalActions> GoalActions;
    @ManyToOne
    @JsonIgnore
    private Goals goals;


}
