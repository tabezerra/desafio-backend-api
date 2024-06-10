package com.usercase.api.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests((requests) -> requests.requestMatchers(HttpMethod.GET, "/conta").hasAnyRole("USER")
				.requestMatchers(HttpMethod.GET, "/conta/**").hasAnyRole("USER")
				.requestMatchers(HttpMethod.PUT, "/conta").hasAnyRole("USER")
				.requestMatchers(HttpMethod.PUT, "/conta/status/**").hasAnyRole("USER")
				.requestMatchers(HttpMethod.POST, "/conta").hasAnyRole("USER")
				.requestMatchers(HttpMethod.GET, "/conta/**").hasAnyRole("USER")
				.requestMatchers(HttpMethod.GET, "/conta/contar-por-periodo").hasAnyRole("USER").anyRequest()
				.authenticated()).httpBasic().and().csrf().disable();

		return http.build();

	}

	@Bean
	public UserDetailsService userDetailsService() {
		@SuppressWarnings("deprecation")
		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}

}
