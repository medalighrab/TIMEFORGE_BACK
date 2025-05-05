package tn.esprit.tic.timeforge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.entity.HealthReminder;
import tn.esprit.tic.timeforge.repository.HealthReminderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HealthReminderServiceImpl implements HealthReminderService {

    @Autowired
    HealthReminderRepository healthReminderRepository;

    @Override
    public HealthReminder addHealthReminder(HealthReminder healthReminder) {
        return healthReminderRepository.save(healthReminder);
    }

    @Override
    public HealthReminder updateHealthReminder(Long id, HealthReminder healthReminder) {
        Optional<HealthReminder> existingReminder = healthReminderRepository.findById(id);
        if (existingReminder.isPresent()) {
            HealthReminder updatedReminder = existingReminder.get();
            updatedReminder.setNotifications(healthReminder.getNotifications());
            return healthReminderRepository.save(updatedReminder);
        }
        return null;
    }

    @Override
    public void deleteHealthReminder(Long id) {
        healthReminderRepository.deleteById(id);
    }

    @Override
    public HealthReminder getHealthReminderById(Long id) {
        return healthReminderRepository.findById(id).orElse(null);
    }

    @Override
    public List<HealthReminder> getAllHealthReminders() {
        return healthReminderRepository.findAll();
    }
}
