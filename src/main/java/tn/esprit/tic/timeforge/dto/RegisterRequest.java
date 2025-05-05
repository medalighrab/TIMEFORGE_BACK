package tn.esprit.tic.timeforge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String name;
    private String password;
    private List<RoleDto> roles;
    private int cin;

}
