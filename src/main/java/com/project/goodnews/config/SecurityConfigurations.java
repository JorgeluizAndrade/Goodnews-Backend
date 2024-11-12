package com.project.goodnews.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.project.goodnews.infrastructure.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

	@Autowired
	private SecurityFilter securityFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
	            .cors(cors -> cors.configurationSource(corsConfigurationSource())) 	
	            .sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "api/auth/login")
						.permitAll().requestMatchers(HttpMethod.POST, "api/auth/register").permitAll()
						.requestMatchers(HttpMethod.GET, "api/posts").permitAll()						.requestMatchers(HttpMethod.GET, "api/posts").permitAll()
						.requestMatchers(HttpMethod.GET, "/").permitAll()
						.requestMatchers(HttpMethod.GET, "api/posts/**").permitAll()
						.requestMatchers(HttpMethod.GET, "api/user/**").permitAll()
						.requestMatchers(HttpMethod.POST, "api/posts").hasAnyRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "api/posts").hasAnyRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "api/posts/**").hasAnyRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "api/user").permitAll()
						.requestMatchers(HttpMethod.DELETE, "api/user/**").hasAnyRole("ADMIN", "USER")
						.requestMatchers(HttpMethod.PUT, "api/user/**").hasAnyRole("ADMIN", "USER")
						.anyRequest().authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public CorsFilter corsFilter() {
	    CorsConfiguration corsConfiguration = new CorsConfiguration();
	    corsConfiguration.setAllowCredentials(true);
	    corsConfiguration.addAllowedOrigin("http://localhost:3000"); 
	    corsConfiguration.addAllowedOrigin("https://goodnews-pink.vercel.app/"); 

	    corsConfiguration.addAllowedHeader("*");
	    corsConfiguration.addAllowedMethod("*");
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", corsConfiguration);
	    return new CorsFilter(source);
	}

	@Bean
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration corsConfiguration = new CorsConfiguration();
	    corsConfiguration.setAllowCredentials(true);
	    corsConfiguration.addAllowedOrigin("http://localhost:3000");
	    corsConfiguration.addAllowedOrigin("https://goodnews-pink.vercel.app/");
	    corsConfiguration.addAllowedHeader("*");
	    corsConfiguration.addAllowedMethod("*");
	    source.registerCorsConfiguration("/**", corsConfiguration);
	    return source;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
