package com.Alice.service.impl;

import com.Alice.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Alice.service.TestService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestServiceImpl implements TestService {
	@Autowired
	private TestMapper mapper;

	@Override
	public String queryNow() {
		//1.注入mapper 
		//2.调用mapper的方法 返回
		return mapper.queryNow();
	}

}
