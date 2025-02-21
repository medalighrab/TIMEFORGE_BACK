package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.tic.timeforge.Entity.Ennum.TypeRewards;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reward implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    TypeRewards typeReward;

    @ManyToOne
    Supervisor supervisor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="employeereward")
    private Set<Employee> Employees;
}
