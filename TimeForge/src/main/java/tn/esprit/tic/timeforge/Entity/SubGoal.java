package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubGoal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    int goalid;
    String title;
    String description;
    Date deadline;
    String progress;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="subgoal")
    private Set<GoalActions> GoalActions;
    @ManyToOne

    private Goals goals;


}
