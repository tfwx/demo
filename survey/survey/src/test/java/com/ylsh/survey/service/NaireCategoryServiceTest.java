package com.ylsh.survey.service;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ylsh.survey.pojo.TbNaireCategory;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/*.xml")
public class NaireCategoryServiceTest {
	
	@Autowired
	private NaireCategoryService naireCategoryService;
	
	@Before
	public void before() {
		System.err.println("-----------Test  Before---------------------");
	}
	
	
	@After
	public void after() {
		System.err.println("-----------Test  After---------------------");
	}
	
	

	@Test
	public void testGetAll() {
		List<TbNaireCategory> list = 
				naireCategoryService.getAll();
		for (TbNaireCategory category : list) {
			System.out.println(category.getId() + "  " + category.getCategoryDesc());
		}
	}
	
	
	@Test
	public void testGetById() {
		TbNaireCategory category = naireCategoryService.get((byte) 1);
		System.out.println(category.getId() + "  " + category.getCategoryDesc());
	}
	
	@Test
	public void testCache() {
		TbNaireCategory c = naireCategoryService.get((byte) 2);
		System.err.println(c.getCategoryDesc());
		System.err.println(c.getParentNode().getCategoryDesc());
	}

}
