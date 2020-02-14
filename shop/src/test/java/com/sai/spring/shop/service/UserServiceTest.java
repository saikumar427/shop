package com.sai.spring.shop.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.sai.spring.shop.bean.User;
import com.sai.spring.shop.repository.UserRepository;

@SpringBootTest
@RunWith(PowerMockRunner.class)
//@PowerMockRunnerDelegate(Cucumber.class)
//@CucumberOptions(features = "classpath:features/user/userService.feature")
public class UserServiceTest {

	public UserServiceTest() {

	}

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	List<User> users = null;

	@Before
	public void setUp() {
		this.users = new ArrayList<User>(Arrays.asList(new User(1L, "sai", "sai.pajworld@gmail.com", "8801177824"),
				new User(2L, "kumar", "kumar@gmail.com", "7702545588"),
				new User(3L, "saikumar", "saikumar@gmail.com", "123345676"),
				new User(4L, "vdf", "kumvdfar@gmail.com", "432423345"),
				new User(5L, "kumvfdfvar", "kumvfdvar@gmail.com", "5345345")));
	}

	@org.junit.Test
	@PrepareForTest(UserService.class)
	public void findAll() {
		MockitoAnnotations.initMocks(UserService.class);
		PowerMockito.mockStatic(UserService.class);
		when(userRepository.findAll()).thenReturn(this.users);
		when(UserService.getTime()).thenReturn(Instant.MIN);
		List<User> users = userService.findAll();
		assertEquals(this.users.size(), users.size());
		verify(userRepository).findAll();
	}

	@Test
	public void getUser() {
		when(userRepository.getOne(1L)).thenReturn(this.users.get(0));
		User user = userService.getUser(1L);
		assertEquals(this.users.get(0), user);
		verify(userRepository).getOne(1L);
	}

	@Test
	public void saveOrUpdateUser() {
		User user = new User(6L, "kumar", "kumar@gmail.com", "7702545588");
		when(userRepository.save(user)).thenReturn(user);
		User user2 = userService.saveOrUpdateUser(user);
		assertEquals(user, user2);
		verify(userRepository).save(user);
	}

	@Test
	public void deleteUser() {
		doNothing().when(userRepository).deleteById(2L);
		userService.deleteUser(2L);
		verify(userRepository, times(1)).deleteById(2L);
	}

	@Test
	public void findByEmail() {
		when(userRepository.findByEmail("sai.pajworld@gmail.com")).thenReturn(this.users.get(0));
		User user = userService.findByEmail("sai.pajworld@gmail.com");
		assertEquals(user, this.users.get(0));
	}

	@Test
	@PrepareForTest(UserService.class)
	public void getTime() {
		PowerMockito.mockStatic(UserService.class);
		PowerMockito.when(UserService.getTime()).thenReturn(Instant.MIN);
		Instant min = UserService.getTime();
		assertEquals(Instant.MIN, min);
	}
}
