package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String username ;
    private String name;
    @JsonIgnore

    private String password;
    private String resetpasswordtoken;
    private int cin;
    private int nbrtasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="teamlead")
    @JsonIgnore
    private Set<Project> Projects;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="teamleader")
    @JsonIgnore
    private Set<Task> Tasks;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="employee11")
    @JsonIgnore
    private Set<Task> Tasksuser;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private Set<Goals> goals;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="teamlead")
    @JsonIgnore
    private Set<Goals> Goals;
    @OneToMany(mappedBy = "userComment")
    private List<ProjectComment> comments;


    public void setUpcomingDeadline(boolean b) {
    }
}
