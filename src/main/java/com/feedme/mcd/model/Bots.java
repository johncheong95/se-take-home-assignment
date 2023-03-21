package com.feedme.mcd.model;

import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.feedme.mcd.runnable.BotsRunnable;

//@Document(collection = "bots")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bots {
	@Id
	private String botsId;
	private String status;
	private BotsRunnable botsRunnable;
	private Thread thread;

	public Bots(String i, String status) {
		this.botsId = i;
		this.status = status;
	}

	public String getBotsId() {
		return botsId;
	}

	public void setBotsId(String botsId) {
		this.botsId = botsId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BotsRunnable getBotsRunnable() {
		return botsRunnable;
	}

	public void setBotsRunnable(BotsRunnable botsRunnable) {
		this.botsRunnable = botsRunnable;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

}
