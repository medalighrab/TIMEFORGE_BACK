package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
//import jdk.internal.org.jline.utils.ShutdownHooks;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Goals implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    ProductivityDshbord productivitydshbord;
    @OneToOne
    private StrategicReminder strategicreminder;

    @ManyToOne
    Teamlead teamlead;
    @ManyToOne
    private Employee employee;
    @ManyToMany(mappedBy="goalss", cascade = CascadeType.ALL)
    private Set<GoalComment> goalcomments;
    @OneToMany(mappedBy = "goals")
    private List<SubGoal> subGoals;

}
