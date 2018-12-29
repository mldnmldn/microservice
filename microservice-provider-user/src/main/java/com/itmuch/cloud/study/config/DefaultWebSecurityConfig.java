//package com.itmuch.cloud.study.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//import com.itmuch.cloud.study.security.DefaultUserDetailsService;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class DefaultWebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Bean
//	public UserDetailsService getUserDetailsService() {
//		return new DefaultUserDetailsService() ;
//	}
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(getUserDetailsService());
//	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//	   http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
//	}
//}
