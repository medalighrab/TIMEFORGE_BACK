package tn.esprit.tic.timeforge.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Repository.ProjectRepository;
import tn.esprit.tic.timeforge.Repository.TaskRepository;
import tn.esprit.tic.timeforge.Repository.UserRepository;

import java.util.Optional;
@Service
public class UserService   {
    @Autowired

    UserRepository userRepository ;
    @Autowired

    private  TaskRepository taskRepository;
    @Autowired

    private  ProjectRepository projectRepository;



    // Trouver un utilisateur par son ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    // Trouver un utilisateur par son nom d'utilisateur
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Trouver un utilisateur par son email
//    public User getUserByEmail(String email) {
//        return userRepository.findByEmail(email);



    }






