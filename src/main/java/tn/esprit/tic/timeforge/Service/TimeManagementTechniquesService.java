package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.TimeManagementTechniques;
import tn.esprit.tic.timeforge.Repository.TimeManagementTechniquesRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeManagementTechniquesService {

    private final TimeManagementTechniquesRepository repository;

    public List<TimeManagementTechniques> getAll() {
        return repository.findAll();
    }

    public Optional<TimeManagementTechniques> getById(Long id) {
        return repository.findById(id);
    }

    public TimeManagementTechniques create(TimeManagementTechniques technique) {
        return repository.save(technique);
    }

    public Optional<TimeManagementTechniques> update(Long id, TimeManagementTechniques updated) {
        return repository.findById(id).map(t -> {
            t.setName(updated.getName());
            t.setDescription(updated.getDescription());
            t.setTimeTracker(updated.getTimeTracker());
            return repository.save(t);
        });
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
