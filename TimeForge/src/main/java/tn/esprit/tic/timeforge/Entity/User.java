package tn.esprit.tic.timeforge.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import tn.esprit.tic.timeforge.Entity.Role;

import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
    public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser") // <-- Colonne "idUser" en base
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String mail;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String photo;
    private boolean confirme = false;
    private String resetToken;

    @Transient
    private String userType;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


//    // Ajoutez cette méthode pour définir le rôle
//    public void setRole(Role role) {
//        this.role = role;
//    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Ensuring ROLE_ prefix is added correctly for Spring Security roles
//        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
//    }
//
//    // Returns username
//
//    // Returns the user's password
//
//
//    // Returns the user's role
//    public Role getRole() {
//        return role;
//    }
//
//
//    // Account non-expired status
//    @Override
//    public boolean isAccountNonExpired() {
//        return true; // Assuming account is never expired; customize as needed
//    }
//
//    // Account non-locked status
//    @Override
//    public boolean isAccountNonLocked() {
//        return true; // Assuming account is never locked; customize as needed
//    }
//
//    // Credentials non-expired status
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true; // Assuming credentials are never expired; customize as needed
//    }
//
//    // Whether the user is enabled or not
//    @Override
//    public boolean isEnabled() {
//        return true; // Assuming the user is always enabled; customize as needed
//    }
//    public Long getId() {
//        return id;
//    }
//
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    // Getter and Setter for 'mail'
//    public String getMail() {
//        return mail;
//    }
//
//    public void setMail(String mail) {
//        this.mail = mail;
//    }
//
//    // Getter and Setter for 'password'
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    // Getter and Setter for 'photo'
//    public String getPhoto() {
//        return photo;
//    }
//
//    public void setPhoto(String photo) {
//        this.photo = photo;
//    }
//
//    // Getter and Setter for 'confirme'
//    public boolean isConfirme() {
//        return confirme;
//    }
//
//    public void setConfirme(boolean confirme) {
//        this.confirme = confirme;
//    }
//
//    // Getter and Setter for 'resetToken'
//    public String getResetToken() {
//        return resetToken;
//    }
//
//    public void setResetToken(String resetToken) {
//        this.resetToken = resetToken;
//    }
}
