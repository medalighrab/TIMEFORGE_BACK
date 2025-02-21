package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductivityDshbord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="productivitydshbord")
    private Set<Goals> Goalss;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="productivitydshbord")
    private Set<Supervisor> Supervisorrs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="productivitydshbord")
    private Set<Project> Projects;

    @ManyToOne
    private Distraction distraction;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="productivitydshbord")
    private Set<Employee> Employees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="productivitydshbord")
    private Set<Task> Tasks;
}
