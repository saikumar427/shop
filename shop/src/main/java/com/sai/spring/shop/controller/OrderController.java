package com.sai.spring.shop.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sai.spring.shop.bean.Item;
import com.sai.spring.shop.bean.Order;
import com.sai.spring.shop.bean.OrderNotFound;
import com.sai.spring.shop.bean.OrderResponse;
import com.sai.spring.shop.repository.ItemRepository;
import com.sai.spring.shop.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ItemRepository itemRepository;

	@PostMapping(path = "/create")
	public OrderResponse createOrder(@RequestParam("products") List<Long> products) {
		Order order = orderService.create(products);
		return buildResponse(order);
	}
	
	@GetMapping("/{orderId}")
	public OrderResponse getOrder(@PathVariable String orderId) {
		Order order = orderService.getOrder(orderId);
		if(order == null)
			throw new OrderNotFoundException(orderId);
		return buildResponse(order);
	}
	
	@GetMapping("/{orderId}/items")
	public List<Item> getOrderItems(@PathVariable String orderId) {
		Order order = orderService.getOrder(orderId);
		if(order == null)
			throw new OrderNotFoundException(orderId);
		return itemRepository.findByOrderByOrderId(orderId);
	}
	
	@GetMapping("/cancel/{orderId}")
	public OrderResponse cancelOrder(@PathVariable String orderId) {
		Order order = orderService.getOrder(orderId);
		if(order == null)
			throw new OrderNotFoundException(orderId);
		orderService.cancelOrder(orderId);
		return buildResponse(orderService.getOrder(orderId));
	}
	
	@GetMapping("/assemble/{orderId}")
	public OrderResponse assembleOrder(@PathVariable String orderId) {
		Order order = orderService.getOrder(orderId);
		if(order == null)
			throw new OrderNotFoundException(orderId);
		orderService.orderAssemble(orderId);
		return buildResponse(orderService.getOrder(orderId));
	}
	
	@GetMapping("/pay/{orderId}")
	public OrderResponse payOrder(@PathVariable String orderId) {
		Order order = orderService.getOrder(orderId);
		if(order == null)
			throw new OrderNotFoundException(orderId);
		orderService.payOrder(orderId);
		return buildResponse(orderService.getOrder(orderId));
	}
	
	private OrderResponse buildResponse(Order order) {
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setItems(order.getItems());
		orderResponse.setOrderId(order.getId());
		orderResponse.setOrderstatus(order.getOrderStatus());
		orderResponse.setCreatedAt(order.getCreatedAt());
		orderResponse.setLastUpdatedAt(order.getLastUpdatedAt());
		return orderResponse;
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<OrderNotFound> springHandleNotFound(Exception ex,HttpServletResponse response) throws IOException {
		OrderNotFound orderResponse = new OrderNotFound();
		orderResponse.setMessage(ex.getMessage());
		return new ResponseEntity<OrderNotFound>(orderResponse, HttpStatus.NOT_FOUND);
    }
}

class OrderNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	OrderNotFoundException(String id){
		super(String.format("Order with id %s not found ", id));
	}
}
