package tn.esprit.tic.timeforge.dto;

import java.time.LocalDate;

public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private LocalDate startDate;
    private LocalDate deadline;
    private int durationInDays;
    private int estimatedHours;
    private String employeeName; // ou id
}
