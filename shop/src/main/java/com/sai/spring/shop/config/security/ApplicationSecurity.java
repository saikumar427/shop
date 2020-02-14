/*
 * package com.sai.spring.shop.config.security;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.config.annotation.authentication.builders.
 * AuthenticationManagerBuilder; import
 * org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter;
 * 
 * @EnableWebSecurity public class ApplicationSecurity extends
 * WebSecurityConfigurerAdapter {
 * 
 * @Autowired private CustomUserDetailsService userDetailsService;
 * 
 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
 * Exception { auth.userDetailsService(userDetailsService); }
 * 
 * }
 */