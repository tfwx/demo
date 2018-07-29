package com.ylsh.survey.mapper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ylsh.survey.pojo.TbRespondentInfo;
import com.ylsh.survey.pojo.TbRespondentInfoExample;
import com.ylsh.survey.util.TimeFormat;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/*.xml")
public class RespondentTest {
	
	
	@Autowired
	private TbRespondentInfoMapper respondentMapper;
	
	
	@Test
	public void updateTme() {
		Random rand = new Random();
		
		Calendar c = Calendar.getInstance();
		
		
		List<TbRespondentInfo> respondentList = respondentMapper.selectByExample(null);
		for (TbRespondentInfo r : respondentList) {
			TbRespondentInfo respondent = new TbRespondentInfo();
			respondent.setId(r.getId());
			
			// 时间范围50秒到120秒 50000-120000  70000
			// 随机产生时间间隔
			
			// 产生随机日期 2018-4-1至 2018-5-20
			// 时间范围 09:00:00至23:00:00
			int month = Math.random() > 0.5 ? 3 : 4;
			int date = rand.nextInt(30) + 1;	// 1 - 30
			int hour = rand.nextInt(15) + 9;	// 9 - 23
			int miniute = rand.nextInt(59) + 1;	// 1 - 59
			int second = rand.nextInt(59) + 1;	// 1 - 59			
			
			if (month == 4 && date > 20) {
				date = rand.nextInt(20) + 1;
			}
			
			c.set(2018, month, date, hour, miniute, second);
			
		
			respondent.setStartTime(c.getTime());			
			
			long duration = rand.nextInt(80000) + 40000;
			Date endTime = new Date(respondent.getStartTime().getTime() + duration);
			respondent.setEndTime(endTime);
			respondent.setDuration(TimeFormat.formatDuration(duration));
			
			System.err.println(TimeFormat.formatDate(respondent.getStartTime()) + "  " + 
					TimeFormat.formatDate(respondent.getEndTime()) + "  " + TimeFormat.formatDuration(respondent.getEndTime().getTime()-respondent.getStartTime().getTime()));
			
			
			
			respondentMapper.updateByPrimaryKeySelective(respondent);
		}
		
	}

}
