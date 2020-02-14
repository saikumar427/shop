package com.sai.spring.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.sai.spring.shop.bean.User;
import com.sai.spring.shop.repository.UserRepository;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class UserServiceScenario {

	@Rule
    public MockitoRule rule = MockitoJUnit.rule();
	@Mock
	private UserRepository userRepository;// = Mockito.mock(UserRepository.class);

	@InjectMocks
	private UserService userService;// = Mockito.mock(UserService.class);

	private User user = null;

	private User user_mock = new User(2L, "kumar", "kumar@gmail.com", "7702545588");

	@Given("^user id as (\\d+)$")
	public void user_id_as(int id) throws Throwable {
		//userService.setUserRepository(userRepository);
		when(userRepository.getOne(Long.valueOf(id))).thenReturn(user_mock);
		this.user = userService.getUser(Long.valueOf(id));
		System.out.println("-----------------------------------");
		System.out.println(this.user);
		System.out.println("-----------------------------------");
	}

	@When("^get user from db with id (\\d+)$")
	public void get_user_from_db_with_id(int arg1) throws Throwable {
	}

	@Then("^user with name \"([^\"]*)\"$")
	public void user_with_name(String name) throws Throwable {
		assertEquals(this.user.getName(), name);
	}

}
