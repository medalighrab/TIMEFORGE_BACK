package tn.esprit.tic.timeforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "tn.esprit.tic.timeforge")
public class TimeForgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeForgeApplication.class, args);
    }

}
