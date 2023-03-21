package com.feedme.mcd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.feedme.mcd.model.Order;
import com.feedme.mcd.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public String save(Order order) {
		return orderRepository.save(order).getOrderId();
	}

	@Override
	public void getOrders(String orderType, String orderStatus) {
		Query query = new Query();
		List<Criteria> criteria = new ArrayList<>();
		if (orderType != null && !orderType.isEmpty()) {
			criteria.add(Criteria.where("orderType").is(orderType));
		}
		if (orderStatus != null && !orderStatus.isEmpty()) {
			criteria.add(Criteria.where("status").is(orderStatus));
		}
	}

}
