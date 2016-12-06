package com.webservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.webservice.security.AuthFailureHandler;
import com.webservice.security.AuthSuccessHandler;
import com.webservice.security.HttpAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class ProjSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	HttpAuthenticationEntryPoint entryPoint;
	
	@Autowired
	AuthSuccessHandler successHandler;
	
	@Autowired
	AuthFailureHandler failureHandler;
	
/*    @Override
    protected void configure(HttpSecurity http) throws Exception { 
        http
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(entryPoint)
        .and()
        .authorizeRequests()
        .antMatchers("/person/*").authenticated()
        .and()
        .formLogin()
        .successHandler(successHandler)
        .failureHandler(failureHandler)
        .and()
        .logout();
    }*/
    
	@Override
	protected void configure(HttpSecurity sec) throws Exception {
		sec.authorizeRequests()
		.antMatchers("/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
		.antMatchers("/person/**").access("hasRole('ROLE_ADMIN')")
		.and().formLogin()
		.successHandler(successHandler)
		.failureHandler(failureHandler)
		.and().logout().deleteCookies("JSESSIONID")
		.and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(entryPoint);
	}
	
	@Autowired
	public void configureGlobel(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("hwt").password("hwt").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
	}
}
