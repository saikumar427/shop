package com.sai.spring.shop.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import com.sai.spring.shop.enums.OrderEvents;
import com.sai.spring.shop.enums.OrderStates;

public class StateMachineOrderListener extends StateMachineListenerAdapter<OrderStates, OrderEvents> {

	private static final Logger log = LoggerFactory.getLogger(StateMachineOrderListener.class);

	@Override
	public void stateChanged(State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
		if(log.isInfoEnabled())
			log.info(String.format("Order State changed from %s to %s ", checkAndReturn(from), checkAndReturn(to)));
	}
	
	private String checkAndReturn(State<OrderStates, OrderEvents> state) {
		if(state != null) {
			return state.getId().toString();
		}
		return null;
	}

}
