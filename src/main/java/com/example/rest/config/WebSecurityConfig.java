package com.example.rest.config;

import com.example.rest.security.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	private final UserDetailsService userDetailsService;

	@Bean
	public JwtFilter getJwtFilter() {
		return new JwtFilter();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoded() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoded());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		/*http.authorizeRequests().antMatchers("/logout","/edit-post","/delete-post","/add-post","/report-post","/edit-post",
				"/like","/set-comment").authenticated().anyRequest().permitAll();
		
//				"/api/v1/auth/send-code", "/swagger-ui.html").permitAll().anyRequest().authenticated();
		http.addFilterBefore(getJwtFilter(), UsernamePasswordAuthenticationFilter.class);*/


	}
}
