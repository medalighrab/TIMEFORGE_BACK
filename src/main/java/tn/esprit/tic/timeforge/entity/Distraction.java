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

public class Distraction  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="distraction")
    @JsonIgnore
    private Set<ProductivityDshbord> ProductivityDshbords;
}
