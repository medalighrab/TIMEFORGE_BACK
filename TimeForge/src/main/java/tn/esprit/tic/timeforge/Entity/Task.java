package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.tic.timeforge.Entity.Ennum.StatusGoal;
import tn.esprit.tic.timeforge.Entity.Ennum.StatusTask;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    int projectid;
    private String name;
    private String description;
    private StatusTask status;
    private int priority;
    private Date date;
    @ManyToOne
    Project project;
    @ManyToOne
    ProductivityDshbord productivitydshbord;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<GoalComment> goalcomments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="task")
    private Set<FocusAnalysis> FocusAnalysiss;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="task")
    private Set<TimeTracker> TimeTRACKERs;
    @ManyToOne
    Employee employee11;
    @ManyToOne
    Teamlead teamleader;
    @OneToOne
    private Deadline deadlinee;
}
