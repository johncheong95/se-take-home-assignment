package com.feedme.mcd.runnable;

import com.feedme.mcd.controller.BotController;
import com.feedme.mcd.controller.OrderController;
import com.feedme.mcd.model.Order;

public class BotsRunnable implements Runnable {

	private String orderId;
	private String status;

	public BotsRunnable(String orderId, String status) {
		this.orderId = orderId;
		this.status = status;
	}

	@Override
	public void run() {
		try {
			System.out.println(" Running Order:" + orderId);
			Thread.sleep(10000);
			System.out.println(" Complete Thread:" + orderId);
			OrderController.processingOrder.remove(orderId);
			Order order = new Order(orderId, "COMPLETED");
			OrderController.completedOrder.add(order);

			BotController.processingBot--;

			System.out.println("Completed: " + OrderController.completedOrder.toArray());
		} catch (InterruptedException e) {
			System.out.println("Interrupted by removal of bot");
		}

	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
