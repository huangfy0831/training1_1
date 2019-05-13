package com.training.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.training.bean.UserBean;

@Controller
@RequestMapping("/redirectAndForward")
public class TestRedirectAndForwardController {

	// Redirect 他のViewに遷移
	// 画面のURLは変わる、BackForwardはできない（更新系の画面で使うか？）
	@RequestMapping("/redirect")
    private String redirect() {
        return "redirect:/returnData/modelAndView";
    }
	
	// Forward 他のControllerに遷移（絶対パスを使うほうがいい）
	// 画面のURLは変わらないので、他の画面に遷移する時は使わないほうがいい
	// 複数Submissionがある場合で使う
	@RequestMapping("/forward")
    private String forward() {
        return "forward:/passParam/hello";
    }

	// ModelMapを使って、URLパラメータとして渡すことができる
	// 1個づつを設定する
	@RequestMapping("/redirect/withModelMap")
    private ModelAndView redirectWithModelMap(ModelMap map) {
		map.addAttribute("param1", 1);
		map.addAttribute("param2", "test");
        return new ModelAndView("redirect:/redirectAndForward/targetPage1", map);
    }

	// 遷移先にも同名のパラメータを定義してから取得できる
	@RequestMapping("/targetPage1")
    private ModelAndView targetPage1(ModelMap map, Integer param1, String param2) {
		map.addAttribute("param1", param1);
		map.addAttribute("param2", param2);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/simpleParam.html");
		return modelAndView;
    }
	
	// RedirectAttributes.addFlashAttributeを使って、セッションとして渡すことができる
	// オブジェクトを設定できる
	@RequestMapping("/redirect/withRedirectAttributes")
    private String redirectWithRedirectAttributes(RedirectAttributes redirectAttributes) {
        UserBean user = new UserBean(166, "sen");
        redirectAttributes.addFlashAttribute("userInfo", user);
        return "redirect:/redirectAndForward/targetPage2";
    }

	// 遷移先には@ModelAttributeでオブジェクトを取得できる
	@RequestMapping("/targetPage2")
    private ModelAndView targetPage2(@ModelAttribute("userInfo")UserBean user) {
		// 画面で使いたい場合は、オブジェクトをModelMapに設定する
		ModelMap map = new ModelMap();
		map.addAttribute("userInfo", user);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/objectParam.html");
		return modelAndView;
    }

	// HttpServletRequestを使ってパラメータを遷移先に共有する
	@RequestMapping("/forward/withHttpServletRequest")
    private String forwardWithHttpServletRequest(HttpServletRequest request) {
		UserBean user = new UserBean(177, "ggo");
		request.setAttribute("userInfo", user);
        return "forward:/redirectAndForward/targetPage3";
    }

	@RequestMapping("/targetPage3")
    private ModelAndView targetPage3(HttpServletRequest request) {
		// 画面で使いたい場合は、オブジェクトをModelMapに設定する
		Object user = request.getAttribute("userInfo");
		ModelMap map = new ModelMap();
		map.addAttribute("userInfo", user);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/objectParam.html");
		return modelAndView;
    }
}
