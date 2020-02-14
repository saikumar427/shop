package com.sai.spring.shop.config.statemachine;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.sai.spring.shop.enums.OrderEvents;
import com.sai.spring.shop.enums.OrderStates;
import com.sai.spring.shop.listeners.StateMachineOrderListener;

@Configuration
@EnableStateMachineFactory
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStates, OrderEvents> {

	private static final Logger log = LoggerFactory.getLogger(OrderStateMachineConfig.class);

	@Override
	public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
		states.withStates()
			.initial(OrderStates.ORDERED)
			.stateEntry(OrderStates.PAYED, context -> {
					String orderId = (String) context.getExtendedState().getVariables().getOrDefault("orderId", "");
					if(log.isInfoEnabled()) {
						log.info(String.format("Order Id %s", orderId));
						log.info("Entered into Payed State");
					}
				})
			.end(OrderStates.PAYED)
			.end(OrderStates.CANCELLED)
			.states(EnumSet.allOf(OrderStates.class));
	}

	@Override
	public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
		config
			.withConfiguration()
			.autoStartup(false)
			.listener(new StateMachineOrderListener());
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
		transitions
		.withExternal()
			.source(OrderStates.ORDERED)
		   .target(OrderStates.ASSEMBLED)
		   .event(OrderEvents.assemble)
		 .and()
		   .withExternal()
		   .source(OrderStates.ASSEMBLED)
		   .target(OrderStates.DELIVERED)
		   .event(OrderEvents.deliver)
		 .and()
		   .withExternal()
		   .source(OrderStates.DELIVERED)
		   .target(OrderStates.INVOICED)
		   .event(OrderEvents.release_invoice)
		 .and()
		   .withExternal()
		   .source(OrderStates.INVOICED)
		   .target(OrderStates.PAYED)
		   .event(OrderEvents.payment_received)
		.and()
			.withExternal() 
			.source(OrderStates.ASSEMBLED)
			.target(OrderStates.PAYED)
			.event(OrderEvents.payment_received)
		 .and()
		   .withExternal()
		   .source(OrderStates.ORDERED)
		   .target(OrderStates.CANCELLED)
		   .event(OrderEvents.cancel)
		 .and() 
		   .withExternal()
		   .source(OrderStates.ASSEMBLED)
		   .target(OrderStates.CANCELLED)
		   .event(OrderEvents.cancel)
		 .and() 
		   .withExternal()
		   .source(OrderStates.DELIVERED)
		   .target(OrderStates.RETURNED)
		   .event(OrderEvents.claim)
		 .and() 
		   .withExternal()
		   .source(OrderStates.INVOICED)
		   .target(OrderStates.RETURNED)
		   .event(OrderEvents.claim)
		 .and() 
		   .withExternal()
		   .source(OrderStates.RETURNED)
		   .target(OrderStates.CANCELLED)
		   .event(OrderEvents.cancel)
		 .and() 
		   .withExternal()
		   .source(OrderStates.RETURNED)
		   .target(OrderStates.ASSEMBLED)
		   .event(OrderEvents.reassemble);
	}

	
}
