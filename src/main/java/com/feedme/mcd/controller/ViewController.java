package com.feedme.mcd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "http://localhost:8090")
public class ViewController {

	@RequestMapping("/home")
	public String index(Model model) {
		return "index";
	}

}
