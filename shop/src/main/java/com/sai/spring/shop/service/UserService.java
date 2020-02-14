package com.sai.spring.shop.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sai.spring.shop.bean.User;
import com.sai.spring.shop.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Cacheable("user")
	public List<User> findAll() {
		getTime();
		return userRepository.findAll();
	}

	@Cacheable(value = "user", key = "#id")
	public User getUser(Long id) {
		return userRepository.getOne(id);
	}

	@CachePut(value = "user", key = "#id")
	public User saveOrUpdateUser(User user) {
		return userRepository.save(user);
	}

	@CacheEvict(value = "user", key = "#id")
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void updateEmail(String newEmail, String oldEmail) {
		userRepository.updateEmailByEmail(newEmail, oldEmail);
	}

	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	public static Instant getTime() {
		return Instant.now();
	}
}
