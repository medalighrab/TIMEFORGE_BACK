package tn.esprit.tic.timeforge.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SessionPlan {
    private String sessionTitle;
    private String techniqueName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
