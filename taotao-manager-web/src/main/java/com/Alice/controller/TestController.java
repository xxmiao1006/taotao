package com.Alice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Alice.service.TestService;


/**
 * 测试使用的controller 查询当前的时间
 * @title TestController.java
 * <p>description</p>
 * <p>company: www.itheima.com</p>
 * @author ljh 
 * @version 1.0
 */
@Controller
public class TestController {

	@Autowired
	private TestService testservice;

	/**
	 * 测试
	 * @return
	 */
	@RequestMapping("/test/qureyNow")
	@ResponseBody
	public String queryNow(){
		//1.引入服务
		//2.注入服务
		//3.调用服务的方法
		return testservice.queryNow();
	}
}
