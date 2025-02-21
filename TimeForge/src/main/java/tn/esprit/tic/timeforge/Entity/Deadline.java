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
public class Deadline implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String details;

    @OneToOne
    @JoinColumn(name = "project_id") // Le nom de la colonne dans la table deadline
    private Project project;


  //  @OneToOne(mappedBy="deadline")
 //   private Task task;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="deadline")
    private Set<Notification> Notifications;
}
