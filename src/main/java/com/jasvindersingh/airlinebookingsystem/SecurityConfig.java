package com.jasvindersingh.airlinebookingsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.jasvindersingh.airlinebookingsystem.services.UserDetailsServiceImpl;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig  {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
     
    /*@Bean
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
    }*/
    
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests()
		.antMatchers( "/js/**","/css/**", "/images/**", "/favicon.ico").permitAll()
		.antMatchers("/login","/register").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login").permitAll()
        .usernameParameter("username")
        .passwordParameter("userpass")
        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
        .defaultSuccessUrl("/index",true).permitAll()
		.and().logout().permitAll()
		.and().csrf().disable()
		http.logout(logout -> logout
            .deleteCookies("JSESSIONID")
        );
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder authMgrBuilder) throws Exception
    {
       authMgrBuilder.authenticationProvider(userDetailsService);
    }*/
    
    
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//security.httpBasic().disable();
		//security.csrf().disable().authorizeRequests().anyRequest().permitAll();
		http.authorizeRequests()
		.antMatchers( "/js/**","/css/**", "/images/**", "/favicon.ico").permitAll()
		.antMatchers("/login","/register","/users/add","/register").permitAll()
		.and().formLogin().loginPage("/login").defaultSuccessUrl("/index").permitAll()
		.and().logout().    //logout configuration
		logoutUrl("/logout"). 
		logoutSuccessUrl("/index")
		.and().csrf().disable();
		http.sessionManagement(session -> session
            .maximumSessions(1)
            .maxSessionsPreventsLogin(true)
        );
		http.logout(logout -> logout
            .deleteCookies("JSESSIONID")
        );
		return http.build();
	}

}
