package tn.esprit.tic.timeforge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthReminder  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @OneToMany(cascade = CascadeType.ALL, mappedBy="HealthReminder")
    @JsonIgnore
    private Set<Notification> Notifications;


}
