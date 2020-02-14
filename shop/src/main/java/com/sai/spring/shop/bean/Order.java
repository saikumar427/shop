package com.sai.spring.shop.bean;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sai.spring.shop.enums.OrderStates;

@Entity
@Table(name = "order_info")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String order_id;
	@Enumerated(EnumType.STRING)
	private OrderStates orderStatus;

	@OneToMany(mappedBy = "order")
	private Set<Item> items = new HashSet<>();

	public Set<Item> getItems() {
		return items;
	}

	public void addItems(Item item) {
		this.items.add(item);
	}

	private Instant createdAt;
	private Instant lastUpdatedAt;

	public String getId() {
		return order_id;
	}

	public void setId(String id) {
		this.order_id = id;
	}

	public OrderStates getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStates orderStatus) {
		this.orderStatus = orderStatus;
	}

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

	@Override
	public String toString() {
		return "Order [id=" + order_id + ", orderStatus=" + orderStatus + ", items=" + items + ", createdAt=" + createdAt
				+ ", lastUpdatedAt=" + lastUpdatedAt + "]";
	}

}
