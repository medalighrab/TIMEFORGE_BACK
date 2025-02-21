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
public class Interface implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String theme;
    String alertSound;
    String layoutPreferences;


    @OneToOne
    private Employee employee;

    @ManyToOne
    Deadline deadline;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="interface1")
    private Set<Notification> Notifications;






}
