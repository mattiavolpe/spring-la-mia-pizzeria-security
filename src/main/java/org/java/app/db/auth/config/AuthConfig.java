package org.java.app.db.auth.config;

import org.java.app.db.auth.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class AuthConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		.requestMatchers(adminRequestMatcher()).hasAuthority("Admin")
		.requestMatchers(userRequestMatcher()).hasAuthority("User")
		.requestMatchers("/**").permitAll()
		.and().formLogin().defaultSuccessUrl("/")
		.and().logout(logout -> logout.logoutSuccessUrl("/"));
		
		return http.build();
	}
	
	@Bean
	UserService userDetailsService() {
		return new UserService();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	private RequestMatcher adminRequestMatcher() {
	    return new RegexRequestMatcher("/(create|edit/[0-9]+|delete/[0-9]+|ingredients/create|ingredients/edit/[0-9]+|ingredients/delete/[0-9]+|[0-9]+/new-deal|deals/edit/[0-9]+|deals/delete/[0-9]+)", null);
	}
	
	private RequestMatcher userRequestMatcher() {
	    return new RegexRequestMatcher("/[0-9]+", null);
	}
}
