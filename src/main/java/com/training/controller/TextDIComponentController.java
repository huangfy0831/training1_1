package com.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.bean.TestDIBean;

@RestController
@RequestMapping("/DI")
public class TextDIComponentController {

	@Autowired
	TestDIBean testDIBean;

	@RequestMapping(value = "/component", method = RequestMethod.GET) 
	private String testComponent() {
		if (testDIBean == null) {
			return "null";
		}
		return "not null";
	}
}
