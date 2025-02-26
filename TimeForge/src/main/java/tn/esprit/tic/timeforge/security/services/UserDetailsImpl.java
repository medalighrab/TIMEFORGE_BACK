package tn.esprit.tic.timeforge.security.services;


import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.tic.timeforge.Entity.Role;
import tn.esprit.tic.timeforge.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;
@Getter
@Setter
@NoArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailsImpl implements UserDetails {
	 Long id;
	String username;
	 String email;
	 String password;
	 String userType;
	Collection<? extends GrantedAuthority> authorities;
	// Ajoutez la méthode getUserType() pour récupérer le type d'utilisateur
	public String getUserType() {
		return this.userType;
	}

	public UserDetailsImpl(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {
		Role role = user.getRole();
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase());

		return new UserDetailsImpl(
				user.getId(),
				user.getUsername(),
				user.getMail(),
				user.getPassword(),
				Collections.singletonList(authority)
		);
	}

	public Long getIdUser() {
		return id;
	}

	public String getMail() {
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDetailsImpl that = (UserDetailsImpl) o;
		return Objects.equals(id, that.id);
	}
}