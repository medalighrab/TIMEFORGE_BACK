package tn.esprit.tic.timeforge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class GamificationProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private int xp;
    private int level;
    private int completedTasks;

    public Long getId() {
        return id;
    }

    public GamificationProfile setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public GamificationProfile setUser(User user) {
        this.user = user;
        return this;
    }

    public int getXp() {
        return xp;
    }

    public GamificationProfile setXp(int xp) {
        this.xp = xp;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public GamificationProfile setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public GamificationProfile setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
        return this;
    }
}

