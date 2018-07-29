package com.ylsh.survey.service;


import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.pojo.TbNaire;
import com.ylsh.survey.pojo.TbNaireCategory;
import com.ylsh.survey.pojo.TbUser;
import com.ylsh.survey.util.JSONUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/*.xml")
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Before
	public void before() {
		System.err.println("------------Test Begin------------------------");
	}
	
	@After
	public void after() {
		System.err.println("------------Test   End------------------------");
	}

	@Test
	public void testLogin() {
		TbUser user = new TbUser();
		user.setPhone("18372562341");
		user.setPassword("123456");
		ResponseResult result = userService.login(user);
		System.out.println(result.getStatus());
		System.out.println(user.getId());
		System.out.println(user.getNick());
	}
	
	@Test
	public void test() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
			TbNaireCategory c1 = new TbNaireCategory();
			TbNaireCategory c2 = new TbNaireCategory();
			TbNaireCategory c3 = new TbNaireCategory();
			c2.setParentNode(c3);
			c1.setParentNode(c2);
			TbNaire naire = new TbNaire();
			naire.setCategory(c1);
			System.err.println(mapper.writeValueAsString(naire));
		
	}
	
	
	public static void fun(String s) {
		System.out.println("String" + s);
	}
	
	public static void fun(Object s) {
		System.out.println("Object" + s);
	}
	
	public static void fun(Integer s) {
		System.out.println("Integer" + s);
	}
	
	public static void main(String[] args) {
		/*ArrayList list = new ArrayList();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		System.err.println(JSONUtil.toJSON(list));*/
		
		fun("");
	}
	
	
	

}
