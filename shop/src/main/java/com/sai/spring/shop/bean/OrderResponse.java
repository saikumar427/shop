package com.sai.spring.shop.bean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

import com.sai.spring.shop.enums.OrderStates;

public class OrderResponse {

	private String orderId;
	
	private Set<Item> items;
	
	private BigDecimal totalCost;
	
	private OrderStates orderstatus;
	
	private Instant createdAt;
	
	private Instant lastUpdatedAt;

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(Instant lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public BigDecimal getTotalCost() {
		calculateTotalCost();
		return this.totalCost;
	}

	public OrderStates getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(OrderStates orderstatus) {
		this.orderstatus = orderstatus;
	}
	
	private void calculateTotalCost() {
		this.totalCost = items.stream().map(i->i.getPrice()).reduce((i,i1)->i.add(i1)).orElse(BigDecimal.ZERO);
	}
}
