package com.sai.spring.shop.repository;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sai.spring.shop.bean.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	@Query(value = "select i from Item i where i.order.order_id=:orderId")
	List<Item> findByOrderByOrderId(@PathParam("orderId") String orderId);
}
