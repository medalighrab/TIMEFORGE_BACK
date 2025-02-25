package tn.esprit.tic.timeforge.Payload.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse {
    String token;
    String type = "Bearer";
    String refreshToken;
    Long id;
    String username;
    String email;
    List<String> roles;
    String role;


}
