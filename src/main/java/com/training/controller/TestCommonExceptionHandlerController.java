package com.training.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// すべてのControllerに適用する
@RestControllerAdvice
public class TestCommonExceptionHandlerController extends ResponseEntityExceptionHandler {

	// ResponseEntityExceptionHandlerを継承して指定しているエラーのキャッチメソッドをoverrideし、
	// 出力メッセージを編集して戻る（defaultは空っぽい）
	// 実際のstatusコードをパラメータとして渡す（統一エラー画面への遷移？）
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		//return super.handleTypeMismatch(ex, headers, status, request);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "API エラー");
        map.put("detail", ex.getMessage());
        map.put("status", status.value());

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Object>(json, HttpStatus.OK);
	}

	// ResponseEntityExceptionHandlerでキャッチできないエラーをキャッチする
	@ExceptionHandler(Exception.class)
    private ResponseEntity<String> onError(Exception ex) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "API エラー");
        map.put("detail", ex.getMessage());
        map.put("status", status.value());

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<String>(json, status);
        
        // TODO ログ出力
        // TODO エラー画面に遷移
    }
}
