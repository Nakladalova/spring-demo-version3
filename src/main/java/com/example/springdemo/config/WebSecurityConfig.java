package com.example.springdemo.config;

import com.example.springdemo.security.CustomLoginFailureHandler;
import com.example.springdemo.security.CustomLoginSuccessHandler;
import com.example.springdemo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/admin").hasAnyRole("ADMIN")
			.antMatchers("/users").authenticated()
			.antMatchers("/search").authenticated()
			.antMatchers("/find").authenticated()
			.anyRequest().permitAll()
				//.antMatchers("/admin").hasAnyRole("ADMIN")
			.and()
			.formLogin().loginPage("/login").permitAll()
				.usernameParameter("username")
				.failureHandler(loginFailureHandler)
				.successHandler(loginSuccessHandler)
				.defaultSuccessUrl("/users")
				.permitAll()
			.and()
			.logout().logoutSuccessUrl("/").permitAll();
	}

	@Autowired
	private CustomLoginFailureHandler loginFailureHandler;

	@Autowired
	private CustomLoginSuccessHandler loginSuccessHandler;
	
}
