package com.fx_boot.poc.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class MainController {

	/* This service helps to check whether the POD service is running or not */
	@RequestMapping("/ping")
	public String ping() {
		return "Service is running at " + System.currentTimeMillis();
	}

	@RequestMapping(value = "/getDetails/{pId}", method = RequestMethod.GET)
	public String getDetails(@PathVariable String pId) {
		return "controller resp: ".concat(pId);
	}
}
