package com.feedme.mcd.service;

import com.feedme.mcd.model.Order;

public interface OrderService {
	public String save(Order order);

	public void getOrders(String orderType, String orderStatus);

}
