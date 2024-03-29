package com.example.springdemo.config;

import com.example.springdemo.security.CustomLoginFailureHandler;
import com.example.springdemo.security.CustomLoginSuccessHandler;
import com.example.springdemo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.nio.file.Path;
import java.nio.file.Paths;

@EnableTransactionManagement
@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
	
	/*@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/

	/*@Autowired
     private PasswordEncoder passwordEncoder;*/

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}

	@Bean
	public HttpFirewall allowSemicolonHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowSemicolon(true);
		return firewall;
	}

	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/users").hasAnyRole("ADMIN")
				.antMatchers("/getinfo").authenticated()
				.antMatchers("/delete_user").authenticated()
				.antMatchers("/images/**").permitAll()
				.antMatchers("/watchdetail").authenticated()
				.antMatchers("/total").authenticated()
				.antMatchers("/shoppingcart").authenticated()
				.antMatchers("/purchase").authenticated()
				.antMatchers("/transfermoney").authenticated()
				.anyRequest().permitAll()
				.and()
				.formLogin().loginPage("/login").permitAll()
				.usernameParameter("username")

				.failureHandler(loginFailureHandler)
				.successHandler(loginSuccessHandler)

				.defaultSuccessUrl("/getinfo")
				.permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/access_denied");
		http.authenticationProvider(authenticationProvider());
		//http.headers().xssProtection().disable();
		http.csrf().disable();
		/*http.sessionManagement()
				.sessionFixation().none();
		/*http
				.sessionManagement()
				.enableSessionUrlRewriting(true);*/



		return http.build();
	}

	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/images/**");
	}

	@Autowired
	private CustomLoginFailureHandler loginFailureHandler;

	@Autowired
	private CustomLoginSuccessHandler loginSuccessHandler;

	
}
