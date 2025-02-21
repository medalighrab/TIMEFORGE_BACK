package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Supervisor")
public class Supervisor extends User implements Serializable {





    @ManyToOne
    HealthReminder healthreminder;
    @ManyToOne
    ProductivityDshbord productivitydshbord;


}
