package com.training.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.training.bean.UserBean;
import com.training.service.CustomRestTemplateCustomizer;

@RestController
@RequestMapping("/restTemplate")
public class TestRestTemplateController {

	// CustomRestTemplateCustomizerを利用して共通設定を読込
	// RestTemplateBuilder.build() = RestTemplate
	@RequestMapping("/restTemplateBuilder")
	private String testRestTemplateBuilder(@RequestParam("hasException") Boolean hasException) {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate template = restTemplateBuilder
				.additionalCustomizers(new CustomRestTemplateCustomizer()).build();

		UserBean user = template.getForObject("/exception/single?hasException=" + hasException, UserBean.class);
		return user.getUserId() + " " + user.getUserName();
	}
}
