package com.ylsh.survey.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ylsh.survey.pojo.TbNaire;
import com.ylsh.survey.pojo.TbQuestion;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/*.xml")
public class TbQuestionMapperTest {
	
	@Autowired
	private TbQuestionMapper questionMapper;
	
	@Autowired
	private TbNaireMapper naireMapper;
	

	@Test
	public void testBatchInsert() {
		List<TbQuestion> questionList = new ArrayList<TbQuestion>();
		TbQuestion question1 = new TbQuestion();
		question1.setQuestionDesc("1");
		question1.setNaireId(1L);
		question1.setCategoryId((byte) 1);
		TbQuestion question2 = new TbQuestion();
		question2.setQuestionDesc("2");
		question2.setNaireId(1L);
		question2.setCategoryId((byte) 1);
		questionList.add(question1);
		questionList.add(question2);
		
		int count = questionMapper.batchInsert(questionList);
		System.out.println(count);
		System.out.println(question1.getId());
	}
	
	@Test
	public void test() {
		TbNaire naire = naireMapper.test(4L);
		System.err.println(naire.getCategory().getCategoryDesc());
	
	}
	
	

}
