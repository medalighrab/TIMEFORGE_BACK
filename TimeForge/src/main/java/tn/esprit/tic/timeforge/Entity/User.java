package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity


public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     long idUser;
     String username ;
     String mail;
     String password;
     int cin;





}
