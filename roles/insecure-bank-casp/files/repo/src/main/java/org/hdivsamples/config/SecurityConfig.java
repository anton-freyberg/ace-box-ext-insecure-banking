package org.hdivsamples.config;

import org.hdivsamples.dao.AccountDao;
import org.hdivsamples.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.util.HashMap;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccountDao accountDao;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationProvider(new CustomAuthenticationProvider(this.accountDao));
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	http
	        .authorizeRequests()
	        	.antMatchers("/resources/**").permitAll()
				.antMatchers("/login*").permitAll()
				.antMatchers("/**").authenticated()
	            .anyRequest().permitAll()
	            .and()
	        .formLogin()
	        	.loginPage("/login").permitAll()
	        	.defaultSuccessUrl("/dashboard")
	        	.successHandler(new SimpleUrlAuthenticationSuccessHandler("/"))
				.failureHandler(failureHandler())
	        	.permitAll()
	        	.and()
	        .csrf().disable()
	        .logout()
	        	.logoutUrl("/j_spring_security_logout")
	        	.logoutSuccessUrl("/login?logout=true");
    }

	private AuthenticationFailureHandler failureHandler(){
		final ExceptionMappingAuthenticationFailureHandler failureHandler = new ExceptionMappingAuthenticationFailureHandler();
		failureHandler.setExceptionMappings(new HashMap<String,String>() {{
			put(InternalAuthenticationServiceException.class.getName(),"/login?error=true");
			put(BadCredentialsException.class.getName(), "/login?authenticationFailure=true");
		}});
		failureHandler.setDefaultFailureUrl("/login?authenticationFailure=true");
		return failureHandler;
	}
    
}
