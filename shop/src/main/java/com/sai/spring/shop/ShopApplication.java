package com.sai.spring.shop;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import com.sai.spring.shop.bean.Product;
import com.sai.spring.shop.bean.User;
import com.sai.spring.shop.enums.Items;
import com.sai.spring.shop.enums.OrderEvents;
import com.sai.spring.shop.enums.OrderStates;
import com.sai.spring.shop.repository.ProductRepository;
import com.sai.spring.shop.repository.UserRepository;
import com.sai.spring.shop.service.OrderService;

@SpringBootApplication
@EnableCaching
public class ShopApplication implements CommandLineRunner {

	@Autowired
	private UserRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StateMachineFactory<OrderStates, OrderEvents> stateMachineFactory;

	@Autowired
	private OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		 inserItems();

		/*
		 * Order order = orderService.create();
		 * orderService.orderAssemble(order.getId());
		 * 
		 * if(order.getId().equals("")) initializeSateMachines();
		 */
	}

	private void initializeSateMachines() {
		StateMachine<OrderStates, OrderEvents> stateMachine = this.stateMachineFactory.getStateMachine();
		StateMachine<OrderStates, OrderEvents> stateMachine2 = this.stateMachineFactory.getStateMachine();
		runStateMachine(stateMachine, "ORDER1234");
		runStateMachine(stateMachine2, "ORDER1235");
	}

	private void runStateMachine(StateMachine<OrderStates, OrderEvents> stateMachine, String orderId) {
		insertUser();
		stateMachine.getExtendedState().getVariables().putIfAbsent("orderId", orderId);
		stateMachine.start();
		stateMachine.sendEvent(OrderEvents.assemble);
		if ("ORDER1235".equals(orderId))
			stateMachine.sendEvent(OrderEvents.cancel);
		stateMachine.sendEvent(OrderEvents.deliver);
		stateMachine.sendEvent(OrderEvents.release_invoice);
		stateMachine.sendEvent(OrderEvents.payment_received);
	}

	private void insertUser() {
		List<User> users = Arrays.asList(new User("sai", "sai.pajworld@gmail.com", "8801177824"),
				new User("kumar", "kumar@gmail.com", "7702545588"),
				new User("saikumar", "saikumar@gmail.com", "123345676"),
				new User("vdf", "kumvdfar@gmail.com", "432423345"),
				new User("kumvfdfvar", "kumvfdvar@gmail.com", "5345345"));
		users.forEach(repository::save);
	}

	private void inserItems() {
		List<Product> items = Arrays.asList(new Product(Items.CAMERA, BigDecimal.valueOf(30000.20)),
				new Product(Items.HARD_DISK, BigDecimal.valueOf(3000.90)),
				new Product(Items.LAPTOP, BigDecimal.valueOf(20300.20)),
				new Product(Items.PHONE, BigDecimal.valueOf(9000.00)),
				new Product(Items.WATCH, BigDecimal.valueOf(4500.50)),
				new Product(Items.KEYBOARD, BigDecimal.valueOf(1300.20)));
		items.forEach(productRepository::save);
	}

}
