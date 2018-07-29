package com.ylsh.survey.poi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.ylsh.survey.mapper.TbNaireMapper;
import com.ylsh.survey.pojo.TbNaire;
import com.ylsh.survey.util.POIUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/*.xml")
public class SimpleDocument { 

	@Autowired
	private TbNaireMapper naireMapper;



	@Test
	public void test() throws Exception { 
		TbNaire naire = naireMapper.selectByPrimaryKey(4L);
		POIUtil.createNaireDocument(naire);
	} 
} 