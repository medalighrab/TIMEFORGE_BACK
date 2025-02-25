package tn.esprit.tic.timeforge.security.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.Entity.*;
import tn.esprit.tic.timeforge.Payload.Request.LoginRequest;
import tn.esprit.tic.timeforge.Payload.Request.RegisterRequest;
import tn.esprit.tic.timeforge.Payload.Response.LoginResponse;
import tn.esprit.tic.timeforge.Reposotory.RoleRepo;
import tn.esprit.tic.timeforge.Reposotory.UserRepo;
import tn.esprit.tic.timeforge.security.jwt.JwtUtils;
import tn.esprit.tic.timeforge.utils.EmailService;

@Service
@AllArgsConstructor
public class AuthService {

    private AuthenticationManager authenticationManager;

    private UserRepo userRepo;

    private RoleRepo roleRepo;// Votre UserRepo pour accéder aux données utilisateur


    private JwtUtils jwtUtils;


    private UserDetailsService userDetailsService;
    private EmailService emailService;


    private BCryptPasswordEncoder passwordEncoder ;

    // Méthode pour l'authentification des utilisateurs et la génération du JWT
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        // Authentification de l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Récupérer les détails de l'utilisateur authentifié
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Générer un JWT avec le nom d'utilisateur et le rôle
        String jwt = jwtUtils.generateJwtToken(userDetails.getUsername(), userDetails.getAuthorities());

        return new LoginResponse(jwt);
    }

    // Méthode pour l'enregistrement d'un nouvel utilisateur
    // Méthode pour l'enregistrement d'un nouvel utilisateur
    public boolean registerUser(RegisterRequest registerRequest) {
        // Vérifier si le nom d'utilisateur existe déjà
        if (userRepo.existsByUsername(registerRequest.getUsername())) {
            System.err.println("Username already exists");
            return false; // Nom d'utilisateur déjà existant
        }

        // Vérifier si l'e-mail existe déjà
        if (userRepo.existsByMail(registerRequest.getEmail())) {
            System.err.println("Email already exists");
            return false; // E-mail déjà existant
        }

        // Créer un nouvel utilisateur selon le rôle
        User newUser;
        switch (registerRequest.getRole().toUpperCase()) {
            case "TEAM_LEAD":
                newUser = new Teamlead();
                break;
            case "SUPERVISOR":
                newUser = new Supervisor();
                break;
            case "EMPLOYEE":
                newUser = new Employee();
                break;
            default:
                return false; // Rôle non valide
        }

        // ✅ Remplir les champs de l'utilisateur
        newUser.setUsername(registerRequest.getUsername());
        newUser.setMail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // 🔐 Hash du mot de passe
        newUser.setConfirme(false); // Par défaut, l'utilisateur n'est pas confirmé
        Role role = roleRepo.findByName(registerRequest.getRole().toUpperCase());

        if (role == null) {
            return false; // Rôle non trouvé en BDD
        }

        newUser.setRole(role); // ✅ Assigne le rôle à l'utilisateur


        // ✅ Sauvegarder l'utilisateur dans la base de données
        newUser.setPhoto("hello");
        userRepo.save(newUser);
        return true;
    }


    // Méthode pour la réinitialisation du mot de passe (si nécessaire)
    public boolean resetPassword(String email) {
        // Trouver l'utilisateur par email
        User user = userRepo.findFirstByMail(email);

        if (user == null) {
            return false; // Utilisateur non trouvé
        }

        // Générer un token de réinitialisation ou un lien (ici un token)
        String resetToken = jwtUtils.generateJwtToken(user.getUsername(), user.getAuthorities());

        // Créer le contenu du mail de réinitialisation
        String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + resetToken;
        String emailContent = "Cliquez sur le lien ci-dessous pour réinitialiser votre mot de passe:\n" + resetLink;

        // Créer l'objet Mail
        Mail mail = new Mail();
        mail.setSubject("Réinitialisation du mot de passe");
        mail.setContent(emailContent);
        mail.setTo(user.getMail());
        mail.setFrom("no-reply@yourapp.com");

        // Envoyer l'email via le service d'email
        emailService.sendSimpleMessage(mail);

        return true; // Réinitialisation réussie
    }

    // Méthode pour la gestion de la déconnexion
    public void logout() {
        // Logique de déconnexion : ici, cela peut être juste une invalidation du token côté client
        // Spring Security gère déjà la suppression du contexte d'authentification après la déconnexion.
    }
}