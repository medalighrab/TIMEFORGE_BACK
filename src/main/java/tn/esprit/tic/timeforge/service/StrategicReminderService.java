package tn.esprit.tic.timeforge.service;

import tn.esprit.tic.timeforge.entity.StrategicReminder;

import java.util.List;

public interface StrategicReminderService {
    StrategicReminder addStrategicReminder(StrategicReminder strategicReminder);
    StrategicReminder updateStrategicReminder(Long id, StrategicReminder strategicReminder);
    void deleteStrategicReminder(Long id);
    StrategicReminder getStrategicReminderById(Long id);
    List<StrategicReminder> getAllStrategicReminders();
}
