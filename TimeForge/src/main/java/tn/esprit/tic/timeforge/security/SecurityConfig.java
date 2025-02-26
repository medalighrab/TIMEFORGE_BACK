package tn.esprit.tic.timeforge.security;



import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tn.esprit.tic.timeforge.security.jwt.AuthEntryPointJwt;
import tn.esprit.tic.timeforge.security.jwt.AuthTokenFilter;
import tn.esprit.tic.timeforge.security.services.UserDetailsServiceImpl;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class SecurityConfig { // extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	@Autowired
	AuthTokenFilter authTokenFilter;

	public SecurityConfig(AuthTokenFilter authTokenFilter) {
		this.authTokenFilter = authTokenFilter;
	}


//	@Override
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable()
//			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//			.authorizeRequests().antMatchers("/api/auth/**").permitAll()
//			.antMatchers("/api/test/**").permitAll()
//			.anyRequest().authenticated();
//
//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable()) // 🔹 Désactiver CSRF pour API REST
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 🔹 Mode stateless
				.authorizeHttpRequests(auth -> auth
						// 🔹 Autorisation des routes publiques
						.requestMatchers("/api/auth/register", "/api/auth/reset-password", "/api/auth/login").permitAll()
						.requestMatchers(
								"/swagger-ui/**",
								"/v3/api-docs/**",
								"/swagger-resources/**",
								"/webjars/**"
						).permitAll() // Swagger accessible sans authentification

						// 🔹 Gestion des rôles avec "hasAuthority" (sans "ROLE_")
						.requestMatchers("/api/admin/**").hasAuthority("ADMIN")
						.requestMatchers("/api/employee/**").hasAuthority("EMPLOYEE")
						.requestMatchers("/api/teamlead/**").hasAuthority("TEAMLEAD")
						.requestMatchers("/api/supervisor/**").hasAuthority("SUPERVISOR")

						// 🔹 Toute autre requête doit être authentifiée
						.anyRequest().authenticated()
				)
				// 🔹 Appliquer la configuration CORS
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				// 🔹 Ajouter le filtre JWT
				.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:4200")); // 🔹 Angular frontend
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true); // 🔹 Autoriser les cookies et les tokens

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//						.allowedOrigins("http://localhost:4200") // Spécifiez le domaine Angular
//						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//						.allowedHeaders("*")
//						.allowCredentials(true); // Autoriser les cookies et les tokens d'authentification
//			}
//		};
//	}




	//// auth google and github
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//				.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(auth -> auth
//						.requestMatchers("/api/auth/**").permitAll()
//						.anyRequest().authenticated()
//				)
//				.oauth2Login(); // Active OAuth2
//
//		return http.build();
//	}
