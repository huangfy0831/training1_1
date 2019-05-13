package com.training.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passParam")
public class TestPathAndParametersController {
	@RequestMapping("hello")
    private String hello() {
        return "SpringBoot!";
    }

	// パスの前に『/』を付けても付けなくても同じです
	@RequestMapping("byPath/WithoutName/{param}")
    private String testPathVariable(@PathVariable String param) {
        return "受け取ったパラメータ：" + param;
    }
	
	// 複数パラメータを渡すことができる、mapping pathは一致する必要がある
	@RequestMapping("/byPath/WithName/{hoge}/test/{nina}")
    private String testPathVariableWithName(@PathVariable("hoge") String hoge, 
    		                                @PathVariable("nina") String nina) {
		return "受け取ったパラメータhoge：" + hoge + " nina：" + nina;
	}
	
	// パラメータ名をはっきり指定している、
	// URLにすべてのパラメータが含まない場合、画面は表示できない
	// 余計なパラメータがあっても大丈夫
	@RequestMapping("/byRequestParam")
    private String testRequestParam(@RequestParam("hoge") String hoge, 
    		                         @RequestParam("nina") String nina) {
		return "受け取ったパラメータhoge：" + hoge + " nina：" + nina;
	}
	
	// 上記の省略書き方、同じです（一番よい書き方かな）
	@RequestMapping("/byRequestParam2")
    private String testRequestParam2(@RequestParam String hoge, 
    		                         @RequestParam String nina) {
		return "受け取ったパラメータhoge：" + hoge + " nina：" + nina;
	}
	
	// URLに同名のパラメータが存在しなくても、画面は表示できる
	// 渡されない場合は、パラメータの値はnullです
	// 注意：Map<String, String> paramsの場合、パラメータは渡されません
	@RequestMapping("/byRequestParam3")
    private String testRequestParam3(String hoge, String nina) {
		return "受け取ったパラメータhoge：" + hoge + " nina：" + nina;
	}

	// methodを指定したら、指定したmethodしかMappingできません
	// methodを省略する場合、すべてのmethodはMappingできる
	@RequestMapping(value = "/byRequestBody", method = RequestMethod.GET)
    private String testRequestBody(@RequestBody String body) {
        return "受け取ったリクエストボディ：" + body;
    }
}
