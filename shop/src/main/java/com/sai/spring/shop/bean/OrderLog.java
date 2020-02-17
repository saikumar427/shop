package com.sai.spring.shop.bean;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sai.spring.shop.enums.OrderStates;

@Entity
public class OrderLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String orderId;
	@Enumerated(EnumType.STRING)
	private OrderStates currentState;
	@Enumerated(EnumType.STRING)
	private OrderStates previousState;
	private Instant logAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderStates getCurrentState() {
		return currentState;
	}

	public void setCurrentState(OrderStates currentState) {
		this.currentState = currentState;
	}

	public OrderStates getPreviousState() {
		return previousState;
	}

	public void setPreviousState(OrderStates previousState) {
		this.previousState = previousState;
	}

	public Instant getLogAt() {
		return logAt;
	}

	public void setLogAt(Instant logAt) {
		if (logAt == null)
			logAt = Instant.now();
		this.logAt = logAt;
	}
}
