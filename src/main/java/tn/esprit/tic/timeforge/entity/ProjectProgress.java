package tn.esprit.tic.timeforge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectProgress  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int projectid;
    String progress;
    Date updated_at;
    @ManyToOne
    @JsonIgnore
    Project project;
}
