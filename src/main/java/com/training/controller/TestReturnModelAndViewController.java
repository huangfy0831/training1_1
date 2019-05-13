package com.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.training.bean.UserBean;

@Controller
@RequestMapping("/returnData")
public class TestReturnModelAndViewController {

	// 画面遷移の同時にModelを戻す
	@RequestMapping(value = "/modelAndView", method = RequestMethod.POST)
    private ModelAndView returnModelAndView() {
		ModelAndView modelAndView = new ModelAndView();
		UserBean user = new UserBean(89, "ko");
		modelAndView.addObject("User", user);
		modelAndView.setViewName("index.html");
        return modelAndView;
    }
}
