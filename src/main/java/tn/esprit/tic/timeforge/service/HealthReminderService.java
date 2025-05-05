package tn.esprit.tic.timeforge.service;

import tn.esprit.tic.timeforge.entity.HealthReminder;

import java.util.List;

public interface HealthReminderService {
    HealthReminder addHealthReminder(HealthReminder healthReminder);
    HealthReminder updateHealthReminder(Long id, HealthReminder healthReminder);
    void deleteHealthReminder(Long id);
    HealthReminder getHealthReminderById(Long id);
    List<HealthReminder> getAllHealthReminders();
}
