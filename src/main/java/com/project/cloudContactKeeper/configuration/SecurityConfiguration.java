package com.project.cloudContactKeeper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf()
		.disable()
		.authorizeHttpRequests()
		.requestMatchers("/admin/**")
		.hasRole("ADMIN")
		.requestMatchers("/user/**")
		.hasRole("USER")
		.requestMatchers("/**")
		.permitAll()
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/processLogin")
		.defaultSuccessUrl("/user/dashboard");
		
		// this will configure authenticationProvider
		http.authenticationProvider(authenticationProvider());

		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		// create Object of DaoAuthenticationProvider
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

		// setting authentication of userDetails and passwordEncoder
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}
	
	/*
	 * @Bean public AuthenticationManager
	 * authenticationManager(AuthenticationConfiguration configuration) throws
	 * Exception { return configuration.getAuthenticationManager(); }
	 */

}
