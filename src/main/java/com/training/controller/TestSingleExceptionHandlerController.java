package com.training.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/exception")
public class TestSingleExceptionHandlerController {

	@RequestMapping(value = "/single", method = RequestMethod.GET)
	private String testSingleException(@RequestParam("hasException") Boolean hasException) {
		if (hasException) {
			return "redirect:/exception/notExistPath";
		}

		return "ok";
	}

    @ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> handleError() {
        Map<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put("message", "許可されていないメソッド");
        errorMap.put("status", HttpStatus.BAD_REQUEST.value());
        return errorMap;
    }
}
