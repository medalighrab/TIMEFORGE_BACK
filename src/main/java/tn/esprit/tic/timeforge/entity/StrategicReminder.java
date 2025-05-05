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
public class StrategicReminder  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy="strategicreminder")
    @JsonIgnore
    private Goals goal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="strategicreminder")
    @JsonIgnore
    private Set<Notification> Notifications;
}
