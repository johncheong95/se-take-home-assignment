package com.feedme.mcd.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feedme.mcd.model.Bots;
import com.feedme.mcd.model.Order;
import com.feedme.mcd.runnable.BotsRunnable;

@RestController
@RequestMapping(path = "api/v1")
public class BotController {
	public static List<Bots> botList = new ArrayList<>();
	public static int processingBot = 0;

	@PostMapping("/bots")
	public ResponseEntity<Bots> createBot() {
		Bots bot = new Bots("BOT-" + (botList.size() + 1), "active");
		botList.add(bot);
		return new ResponseEntity<>(bot, HttpStatus.CREATED);
	}

	@GetMapping("/bots")
	public ResponseEntity<List<Bots>> getBots() {
		return new ResponseEntity<>(botList, HttpStatus.OK);
	}

	@PostMapping("/bots/process/order")
	public ResponseEntity<Bots> process() {
		processOrder();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/bots")
	public ResponseEntity<Bots> deleteBot() {
		if (botList.size() <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		int lastIndex = botList.size() - 1;

		Bots bot = removeBotReplaceOrder(lastIndex);
		return new ResponseEntity<>(bot, HttpStatus.OK);

	}

	private Bots removeBotReplaceOrder(int lastIndex) {
		Bots bot = botList.remove(lastIndex);
		BotsRunnable executeOrder = bot.getBotsRunnable();
		Thread botThread = bot.getThread();
		if (botThread != null || executeOrder != null) {
			botThread.interrupt();
			processingBot--;
			String orderId = executeOrder.getOrderId();
			OrderController.orderList.put(orderId, new Order(orderId, "PENDING"));
			OrderController.processingOrder.remove(orderId);
			if (orderId.contains("NOR")) {
				OrderController.orderQueue.add(OrderController.vipOrderPointer + OrderController.normalOrderPointer,
						orderId);
			} else {
				OrderController.orderQueue.add(OrderController.vipOrderPointer, orderId);
			}
		}

		return bot;
	}

	public void processOrder() {
		List<String> orderQueue = OrderController.orderQueue;
		if (orderQueue.size() <= 0) {
			return;
		}
		if (botList.size() <= 0 || processingBot >= botList.size()) {
			return;
		}
		try {
			String orderId = orderQueue.get(0);
			BotsRunnable executeOrder = new BotsRunnable(orderId, "PROCESSING");
			Thread botThread = new Thread(executeOrder);
			Bots bot = botList.get(processingBot);
			bot.setThread(botThread);
			bot.setBotsRunnable(executeOrder);
			OrderController.processingOrder.put(orderId, new Order(orderId, "PROCESSING"));
			OrderController.orderList.remove(orderId);
			processingBot++;
			botThread.start();
			orderQueue.remove(0); // order execute always remove the first queue
			if (executeOrder.getOrderId().contains("VIP")) {
				OrderController.vipOrderPointer--;
			} else if (executeOrder.getOrderId().contains("NOR")) {
				OrderController.normalOrderPointer--;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
