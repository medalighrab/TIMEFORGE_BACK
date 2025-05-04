package tn.esprit.tic.timeforge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Payload.Request.LoginRequest;
import tn.esprit.tic.timeforge.Payload.Request.RegisterRequest;
import tn.esprit.tic.timeforge.Payload.Response.LoginResponse;
import tn.esprit.tic.timeforge.Reposotory.UserRepo;
import tn.esprit.tic.timeforge.security.jwt.JwtUtils;
import tn.esprit.tic.timeforge.security.services.AuthService;
import tn.esprit.tic.timeforge.security.services.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;  // Injection d'AuthenticationManager

    @Autowired
    private UserRepo utilisateurRepo;  // Injection de UserRepo

    @Autowired
    private JwtUtils jwtUtils;  // Injection de JwtUtils

        @Autowired
        private AuthService authService;

//    http://localhost:8080/swagger-ui/index.html


        // Méthode pour l'enregistrement d'un nouvel utilisateur
        @PostMapping("/register")
        public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
            boolean isRegistered = authService.registerUser(registerRequest);
            System.err.println(registerRequest+"       "+isRegistered);
            if (isRegistered) {
                return ResponseEntity.ok("User registered successfully!");
            } else {
                return ResponseEntity.badRequest().body("Error: Username or email already exists, or invalid role.");
            }
        }

            // Méthode pour l'authentification d'un utilisateur et génération du JWT
            @PostMapping("/login")
            public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
                LoginResponse loginResponse = authService.authenticateUser(loginRequest);
                return ResponseEntity.ok(loginResponse);
            }

    // Méthode pour la réinitialisation du mot de passe (via email)
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        boolean isReset = authService.resetPassword(email);
        if (isReset) {
            return ResponseEntity.ok("Password reset link has been sent to your email.");
        } else {
            return ResponseEntity.badRequest().body("Error: No user found with this email.");
        }
    }


        // Méthode pour la déconnexion de l'utilisateur
        @PostMapping("/logout")
        public ResponseEntity<String> logout() {
            authService.logout();
            return ResponseEntity.ok("User logged out successfully.");
        }
    }

