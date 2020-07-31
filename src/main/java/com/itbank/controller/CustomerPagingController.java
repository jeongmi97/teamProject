package com.itbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerPagingController {
	
//	@RequestMapping("join/")	
//	public void join() {}	
	
	@RequestMapping("login/")
	public void login() {}

	@RequestMapping("emailHelp/")
	public void emailHelp() {}
	
	@RequestMapping("helpPw/")
	public void helpPw() {}
	
	@RequestMapping("helpEmail/")
	public void helpEmail() {}
	
	@RequestMapping("userInfo/")
	public void userInfo() {}
}
