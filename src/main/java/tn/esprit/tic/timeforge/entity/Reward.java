package tn.esprit.tic.timeforge.entity;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.tic.timeforge.entity.Ennum.TypeRewards;

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
