package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.StrategicReminder;
import tn.esprit.tic.timeforge.Repository.StrategicReminderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StrategicReminderServiceImpl implements StrategicReminderService {

    @Autowired
    StrategicReminderRepository strategicReminderRepository;

    @Override
    public StrategicReminder addStrategicReminder(StrategicReminder strategicReminder) {
        return strategicReminderRepository.save(strategicReminder);
    }

    @Override
    public StrategicReminder updateStrategicReminder(Long id, StrategicReminder strategicReminder) {
        Optional<StrategicReminder> existingReminder = strategicReminderRepository.findById(id);
        if (existingReminder.isPresent()) {
            StrategicReminder updatedReminder = existingReminder.get();
            updatedReminder.setGoal(strategicReminder.getGoal());
            updatedReminder.setNotifications(strategicReminder.getNotifications());
            return strategicReminderRepository.save(updatedReminder);
        }
        return null;
    }

    @Override
    public void deleteStrategicReminder(Long id) {
        strategicReminderRepository.deleteById(id);
    }

    @Override
    public StrategicReminder getStrategicReminderById(Long id) {
        return strategicReminderRepository.findById(id).orElse(null);
    }

    @Override
    public List<StrategicReminder> getAllStrategicReminders() {
        return strategicReminderRepository.findAll();
    }
}
