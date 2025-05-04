package tn.esprit.tic.timeforge.Payload.Request;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRequest {

    String username ;
    String mail;
    String password;
    int cin;
    String photo;
    boolean confirme= false ;



}
