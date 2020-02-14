package com.sai.spring.shop.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sai.spring.shop.bean.User;
import com.sai.spring.shop.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<User> getAllUsers(){
		return userService.findAll();
	}
	
	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User user){
		User userInfo = userService.saveOrUpdateUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(userInfo.getId());
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(path = "/{id}")
	public User getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}
}
