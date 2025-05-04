package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long projectid;
    String name;
    String description;
    Date deadline;
    File cahiercharge;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="project")
    private Set<ProjectComment> ProjectComments;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="project")
    private Set<ProjectProgress> ProjectProgresss;

    @OneToOne(mappedBy = "project")
    private Deadline deadlinee;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="project")
    private Set<Task> Taskss;
    @ManyToOne
    Teamlead teamlead;
    @ManyToOne
    ProductivityDshbord productivitydshbord;
}
