package tn.esprit.tic.timeforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TimeForgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeForgeApplication.class, args);
    }

}
