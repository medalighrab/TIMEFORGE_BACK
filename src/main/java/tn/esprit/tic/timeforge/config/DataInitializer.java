package tn.esprit.tic.timeforge.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import tn.esprit.tic.timeforge.entity.Ennum.RoleName;
import tn.esprit.tic.timeforge.service.security.ServiceUser;

@Configuration
public class DataInitializer implements CommandLineRunner {
    private final ServiceUser userService;


    public DataInitializer( ServiceUser userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userService.createRoleIfNotExist(RoleName.ROLE_ADMIN);
        userService.createRoleIfNotExist(RoleName.ROLE_EMPLOYEE);
        userService.createRoleIfNotExist(RoleName.ROLE_TEAMLEAD);
        userService.createRoleIfNotExist(RoleName.ROLE_VISITEUR);
        userService.createAdminIfNotExist();


    }
}
