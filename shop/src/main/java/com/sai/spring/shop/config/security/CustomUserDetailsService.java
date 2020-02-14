/*
 * package com.sai.spring.shop.config.security;
 * 
 * import java.util.ArrayList;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.security.core.GrantedAuthority; import
 * org.springframework.security.core.userdetails.User; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.stereotype.Service;
 * 
 * import com.sai.spring.shop.service.UserService;
 * 
 * @Service public class CustomUserDetailsService implements UserDetailsService{
 * 
 * 
 * @Autowired private UserService userservice;
 * 
 * @Override public UserDetails loadUserByUsername(String username){
 * BCryptPasswordEncoder encoder = passwordEncoder();
 * com.sai.spring.shop.bean.User user = userservice.findByName(username); return
 * new User(user.getName(), encoder.encode(user.getPhoneNumber()), new
 * ArrayList<GrantedAuthority>()); }
 * 
 * 
 * @Bean public BCryptPasswordEncoder passwordEncoder() { return new
 * BCryptPasswordEncoder(); }
 * 
 * 
 * }
 */