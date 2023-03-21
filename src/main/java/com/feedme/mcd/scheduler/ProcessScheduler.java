package com.feedme.mcd.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.feedme.mcd.controller.BotController;

@Component
public class ProcessScheduler {
	private static BotController botController = new BotController();

	@Scheduled(fixedRate = 1000)
	public void loadData() {
		botController.processOrder();
	}
}
