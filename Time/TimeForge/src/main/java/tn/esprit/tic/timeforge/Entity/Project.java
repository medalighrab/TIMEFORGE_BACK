package tn.esprit.tic.timeforge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.tic.timeforge.Entity.Ennum.StatusProject;
import tn.esprit.tic.timeforge.Entity.Ennum.StatusTask;

import java.io.File;
import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@NoArgsConstructor
    @AllArgsConstructor
    public class Project  {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long projectid;
        String name;
        String description;
        Date deadline;
        Date old_deadline;
        File cahiercharge;

    @Enumerated(EnumType.STRING)
    private StatusProject status = StatusProject.IN_PROGRESS;  // Initialisation à IN_PROGRESS


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="project")
    private Set<ProjectComment> ProjectComments;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="project")
    private Set<ProjectProgress> ProjectProgresss;
    @JsonIgnore
    @OneToOne(mappedBy = "project")
    private Deadline deadlinee;
    boolean upcamingdealine=false;

    @OneToMany(cascade = CascadeType.ALL,fetch =FetchType.LAZY ,mappedBy="project")
    @JsonIgnoreProperties("project")
    @JsonManagedReference
    private Set<Task> Taskss;
    @JsonIgnore
    @ManyToOne
    User teamlead;
    @JsonIgnore
    @ManyToOne
    ProductivityDshbord productivitydshbord;


    public boolean isUpcomingDeadline() {
        return upcamingdealine;
    }

    // Setter pour upcomingDeadline
    public void setUpcomingDeadline(boolean upcomingDeadline) {
        this.upcamingdealine = upcomingDeadline;
    }



    public int getTaskDuration() {
        if (Taskss == null) return 0;
        return Taskss.stream()
                .filter(task -> task.getStartDate() != null && task.getDeadline() != null)
                .mapToInt(task -> (int) ChronoUnit.DAYS.between(task.getStartDate(), task.getDeadline()))
                .sum();
    }

    public int getTaskStatus() {
        if (Taskss == null) return 0;
        return (int) Taskss.stream()
                .filter(task -> task.getStatus() != StatusTask.DONE)
                .count(); // Compte les tâches qui restent à faire : TODO, IN_PROGRESS, BLOCKED
    }

    public int getBlockedDuration() {
        if (Taskss == null) return 0;
        return Taskss.stream()
                .filter(task -> task.getStatus() == StatusTask.BLOCKED)
                .filter(task -> task.getStartDate() != null && task.getDeadline() != null)
                .mapToInt(task -> (int) ChronoUnit.DAYS.between(task.getStartDate(), task.getDeadline()))
                .sum();
    }

    public int getProjectDuration() {
        if (Taskss == null || Taskss.isEmpty()) return 0;

        Optional<LocalDate> minDate = Taskss.stream()
                .filter(task -> task.getStartDate() != null)
                .map(Task::getStartDate)
                .min(LocalDate::compareTo);

        Optional<LocalDate> maxDate = Taskss.stream()
                .filter(task -> task.getDeadline() != null)
                .map(Task::getDeadline)
                .max(LocalDate::compareTo);

        if (minDate.isEmpty() || maxDate.isEmpty()) return 0;

        return (int) ChronoUnit.DAYS.between(minDate.get(), maxDate.get());
    }

}
