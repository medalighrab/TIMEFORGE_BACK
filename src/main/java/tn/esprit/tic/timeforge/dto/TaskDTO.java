package tn.esprit.tic.timeforge.dto;

import tn.esprit.tic.timeforge.entity.Ennum.StatusTask;

import java.time.LocalDate;

public class TaskDTO {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate deadline;
    private boolean isdeleted;
    private StatusTask status;

    public TaskDTO(String name, String description, LocalDate startDate, LocalDate deadline, boolean isdeleted, StatusTask status) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.deadline = deadline;
        this.isdeleted = isdeleted;
        this.status = status;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public boolean isIsdeleted() { return isdeleted; }
    public void setIsdeleted(boolean isdeleted) { this.isdeleted = isdeleted; }

    public StatusTask getStatus() { return status; }
    public void setStatus(StatusTask status) { this.status = status; }
}
