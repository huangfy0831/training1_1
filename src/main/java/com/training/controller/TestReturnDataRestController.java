package com.training.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.bean.UserBean;

@RestController
@RequestMapping("/returnData")
public class TestReturnDataRestController {
	// text/plain;charset=UTF-8
	@RequestMapping("/string")
    private String returnString() {
        return "戻り値はString";
    }

	// application/json;charset=UTF-8
	@RequestMapping("/bean")
    private UserBean returnBean() {
		UserBean user = new UserBean(1, "mogo");
        return user;
    }

	// application/json;charset=UTF-8
	@RequestMapping("/map")
    private Map<String, Object> returnMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("hoge", "ぴよ");
		map.put("moge", 999);
        return map;
    }

	// producesが指定されない場合は、application/json
	// producesで指定されたMedieTypeとして戻す
	// application/octet-streamを指定する場合は、ファイル種類により自動的に適用できる
	// 例えば、テキストを戻す時は、text/plainです。
	// 自動的に適用できない場合もある、その時のContent-Typeはapplication/octet-stream
	@RequestMapping(value="/file", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
    private Resource returnFile() {
		File file = new File("E:\\conf\\ftp-connection.properties");
		return new FileSystemResource(file);
    }
	
	@RequestMapping("/exceptionHandler")
	public String testException() throws Exception {
		throw new RuntimeException( "エラー発生" );
	}
}
