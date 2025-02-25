package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Entity
@DiscriminatorValue("Teamlead")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Teamlead extends User implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy="teamlead")
    private Set<Project> Projects;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="teamlead")
    private Set<Goals> Goals;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="teamleader")
    private Set<Task> Tasks;

}
