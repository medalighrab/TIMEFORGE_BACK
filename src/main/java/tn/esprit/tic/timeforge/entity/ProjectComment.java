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
public class ProjectComment   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    Long projectId;
    Long userId;
    String comment;
    Date createdAt;
    @ManyToOne
    @JsonIgnore
    Project project;
}
