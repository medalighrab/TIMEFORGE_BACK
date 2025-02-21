package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Employee")

public class Employee extends User implements Serializable {



    @ManyToOne
    Reward employeereward;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="employee")
    private Set<CompletedTasksHistory> ComletedTasksHistorys;

    @OneToOne(mappedBy = "employee")  // mappedBy référence l'attribut dans l'entité Interface
    private Interface employeeInterface;

    @ManyToOne
    ProductivityDshbord productivitydshbord;
    @OneToMany(mappedBy = "employee")
    private Set<Goals> goals;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="employee11")
    private Set<Task> Tasks;

}
