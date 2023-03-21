package com.feedme.mcd.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feedme.mcd.model.Order;

@RestController
@RequestMapping(path = "api/v1")
public class OrderController {
	public static Map<String, Order> orderList = new HashMap<>();
	public static List<String> orderQueue = new ArrayList<>();
	public static List<Order> completedOrder = new ArrayList<>();
	public static Map<String, Order> processingOrder = new HashMap<>();
	private static int orderCount = 0;
	static int vipOrderPointer = 0;
	static int normalOrderPointer = 0;

	@PostMapping("/orders")
	public ResponseEntity<Order> createOrder() {
		Order order = new Order(generateOrderId("NOR"), "PENDING");
		orderList.put(order.getOrderId(), order);
		orderQueue.add(vipOrderPointer + normalOrderPointer, order.getOrderId());
		normalOrderPointer++;
		System.out.println(orderQueue);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@PostMapping("/orders/vip")
	public ResponseEntity<Order> createVipOrder() {
		Order order = new Order(generateOrderId("VIP"), "PENDING");
		orderList.put(order.getOrderId(), order);
		orderQueue.add(vipOrderPointer, order.getOrderId());
		vipOrderPointer++;
		System.out.println(orderQueue);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getOrders(@RequestParam(required = false) String orderStatus) {

		List<Order> response = null;

		if ("PROCESSING".equals(orderStatus)) {
			response = processingOrder.values().stream().collect(Collectors.toList());
		} else if ("COMPLETED".equals(orderStatus)) {
			response = completedOrder;
		} else {
			response = orderList.values().stream().collect(Collectors.toList());
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private String generateOrderId(String orderType) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		StringBuilder orderId = new StringBuilder();
		orderCount++;
		orderId.append(orderType + "-" + formatter.format(new Date()) + String.format("%03d", orderCount));
		return orderId.toString();
	}
}
