package com.sai.spring.shop.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sai.spring.shop.bean.Item;
import com.sai.spring.shop.bean.Order;
import com.sai.spring.shop.bean.Product;
import com.sai.spring.shop.enums.OrderEvents;
import com.sai.spring.shop.enums.OrderStates;
import com.sai.spring.shop.repository.ItemRepository;
import com.sai.spring.shop.repository.OrderRepository;
import com.sai.spring.shop.repository.ProductRepository;

@Service
@Transactional
public class OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderService.class);
	private static final String ORDER_ID_HEADER = "orderId";

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StateMachineFactory<OrderStates, OrderEvents> factory;

	public Order create(List<Long> productList) {
		List<Product> products = productRepository.findByIdIn(productList);
		Order order = new Order();
		order.setOrderStatus(OrderStates.ORDERED);
		order.setCreatedAt(Instant.now());
		orderRepository.save(order);

		products.stream().forEach(product -> {
			Item item = new Item();
			item.setItem(product.getItem());
			item.setOrder(order);
			item.setPrice(product.getPrice());
			item.setProductId(product.getId());
			order.addItems(item);
			itemRepository.save(item);
		});

		return order;
	}

	public Order getOrder(String orderId) {
		return orderRepository.findById(orderId).orElse(null);
	}

	public void orderAssemble(String orderId) {
		StateMachine<OrderStates, OrderEvents> stateMachine = this.build(orderId);
		Message<OrderEvents> fulfillmentMessage = MessageBuilder.withPayload(OrderEvents.assemble)
				.setHeader(ORDER_ID_HEADER, orderId).build();

		stateMachine.sendEvent(fulfillmentMessage);

	}

	public void cancelOrder(String orderId) {
		StateMachine<OrderStates, OrderEvents> stateMachine = this.build(orderId);
		Message<OrderEvents> cancleMessage = MessageBuilder.withPayload(OrderEvents.cancel)
				.setHeader(ORDER_ID_HEADER, orderId).build();

		stateMachine.sendEvent(cancleMessage);
	}
	
	public void payOrder(String orderId) {
		StateMachine<OrderStates, OrderEvents> stateMachine = this.build(orderId);
		Message<OrderEvents> message = MessageBuilder.withPayload(OrderEvents.payment_received)
				.setHeader(ORDER_ID_HEADER, orderId).build();

		stateMachine.sendEvent(message);
	}

	private StateMachine<OrderStates, OrderEvents> build(String orderId) {
		Order order = this.orderRepository.getOne(orderId); // Retrieve orderId on DB
		String orderIdKey = String.valueOf(order.getId()); // Convert the ID to String
		StateMachine<OrderStates, OrderEvents> stateMachine = this.factory.getStateMachine(orderIdKey); // Get the
																										// StateMachine
																										// with the
																										// specific ID

		stateMachine.stop(); // stop state machine from running

		/**
		 * Override the state machine's state/event/transition
		 *
		 * This is useful when you need to add new metadata for the state machine before
		 * going to a certain state/event/transition.
		 *
		 */
		stateMachine.getStateMachineAccessor().doWithAllRegions(sma -> {

			/**
			 * This interceptor exists if you want to persist the state of the state machine
			 * to the DB or something else, maybe add some metadata on the DB about the
			 * current information of the state machine's state or something like that.
			 */
			sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<OrderStates, OrderEvents>() {

				@Override
				public Message<OrderEvents> preEvent(Message<OrderEvents> message,
						StateMachine<OrderStates, OrderEvents> stateMachine) {

					log.info("[XXXX] PRE EVENT");
					log.info("[XXXX] MESSAGE " + message);
					log.info("[XXXX] STATE MACHINE " + stateMachine);

					return super.preEvent(message, stateMachine);
				}

				@Override
				public StateContext<OrderStates, OrderEvents> preTransition(
						StateContext<OrderStates, OrderEvents> stateContext) {

					log.info("[XXXX] PRE TRANSITION:");
					log.info("[XXXX] STATE CONTEXT " + stateContext);

					return super.preTransition(stateContext);
				}

				@Override
				public void preStateChange(State<OrderStates, OrderEvents> state, Message<OrderEvents> message,
						Transition<OrderStates, OrderEvents> transition,
						StateMachine<OrderStates, OrderEvents> stateMachine) {

					Optional.ofNullable(message).ifPresent(msg -> {
						Optional.ofNullable(msg.getHeaders().getOrDefault(ORDER_ID_HEADER, "")).ifPresent(orderId -> {
							Order order1 = orderRepository.getOne(orderId.toString());
							order1.setOrderStatus(state.getId()); // This is the one responsible for changing the
							order1.setLastUpdatedAt(Instant.now()); // state
							orderRepository.save(order1);

						});
					});
				}

				@Override
				public StateContext<OrderStates, OrderEvents> postTransition(
						StateContext<OrderStates, OrderEvents> stateContext) {

					log.info("[XXXX] POST TRANSITION:");
					log.info("[XXXX] STATE CONTEXT ", stateContext);

					return super.postTransition(stateContext);
				}

				@Override
				public void postStateChange(State<OrderStates, OrderEvents> state, Message<OrderEvents> message,
						Transition<OrderStates, OrderEvents> transition,
						StateMachine<OrderStates, OrderEvents> stateMachine) {
					log.info("[XXXX] POST STATE CHANGE");

					log.info("[XXXX] State: " + state);
					log.info("[XXXX] Message: " + message);
					log.info("[XXXX] Transition: " + transition);
					log.info("[XXXX] State Machine: " + stateMachine);

					super.postStateChange(state, message, transition, stateMachine);
				}
			});

			/**
			 * This tells the state machine to force itself to go to a separated state
			 * instead of defaulting to it's initial state.
			 */
			sma.resetStateMachine(new DefaultStateMachineContext<>(order.getOrderStatus(), null, null, null));
		});

		stateMachine.start(); // start the state machine once again
		return stateMachine;
	}
}
