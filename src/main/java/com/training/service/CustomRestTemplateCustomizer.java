package com.training.service;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.training.handle.CustomResponseErrorHandler;

public class CustomRestTemplateCustomizer implements RestTemplateCustomizer {

	@Override
	public void customize(RestTemplate restTemplate) {		
		// タイムアウト時間や承認などを設定できる
		// rootURIを設定できる
		// カスタマイズエラー応答を設定できる
		new RestTemplateBuilder()
				.setConnectTimeout(Duration.ofSeconds(3))
		        .setReadTimeout(Duration.ofSeconds(5))
		        .basicAuthentication("userId", "Password")
		        .uriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:7777"))
		        .errorHandler(new CustomResponseErrorHandler())
		        .configure(restTemplate);
	}
}
