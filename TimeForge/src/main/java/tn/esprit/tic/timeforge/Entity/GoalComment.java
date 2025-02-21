package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoalComment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    int goalid;
    int userid;
    String comment;
    Date createdAt;
    @ManyToMany(mappedBy="goalcomments", cascade = CascadeType.ALL)
    private Set<Task> tasks;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Goals> goalss;
}
