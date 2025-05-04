package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Goals  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    @JsonIgnore
    ProductivityDshbord productivitydshbord;
    @OneToOne
    @JsonIgnore
    private StrategicReminder strategicreminder;

    @ManyToOne
    @JsonIgnore
    User teamlead;
    @ManyToOne
    @JsonIgnore
    private User employee;
    @ManyToMany(mappedBy="goalss", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<GoalComment> goalcomments;
    @OneToMany(mappedBy = "goals")
    @JsonIgnore
    private List<SubGoal> subGoals;

    @ManyToOne
    @JsonIgnore
    Task task13;

    public Goals(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Goals(Long id, String title, String description, ProductivityDshbord productivitydshbord, User teamlead, StrategicReminder strategicreminder, User employee, Set<GoalComment> goalcomments, List<SubGoal> subGoals, Task task13) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.productivitydshbord = productivitydshbord;
        this.teamlead = teamlead;
        this.strategicreminder = strategicreminder;
        this.employee = employee;
        this.goalcomments = goalcomments;
        this.subGoals = subGoals;
        this.task13 = task13;
    }

    public void setTask13(Task task13) {
        this.task13 = task13;
    }
}
