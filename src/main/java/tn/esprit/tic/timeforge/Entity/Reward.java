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
public class Reward  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    TypeRewards typeReward;



}
