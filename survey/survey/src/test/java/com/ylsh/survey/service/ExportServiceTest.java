package com.ylsh.survey.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylsh.survey.mapper.TbAnswerRecordMapper;
import com.ylsh.survey.mapper.TbBriefAnswerMapper;
import com.ylsh.survey.mapper.TbNaireMapper;
import com.ylsh.survey.mapper.TbProvinceMapper;
import com.ylsh.survey.mapper.TbRespondentInfoMapper;
import com.ylsh.survey.pojo.TbBriefAnswer;
import com.ylsh.survey.pojo.TbBriefAnswerExample;
import com.ylsh.survey.pojo.TbNaire;
import com.ylsh.survey.pojo.TbOption;
import com.ylsh.survey.pojo.TbQuestion;
import com.ylsh.survey.pojo.TbRespondentInfo;
import com.ylsh.survey.util.NumberFormatUtil;
import com.ylsh.survey.util.POIUtil;
import com.ylsh.survey.util.TimeFormat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class ExportServiceTest {


	@Autowired
	private TbNaireMapper naireMapper;


	@Autowired
	private TbAnswerRecordMapper answerRecordMapper;

	@Before
	public void before() {
		System.err.println("-----------Test  Before---------------------");
	}


	@After
	public void after() {
		System.err.println("-----------Test  After---------------------");
	}



	@Test
	public void test() throws Exception {
		long startTime, endTime;
		/*
		startTime = System.currentTimeMillis();
		// 获取问卷基本信息
		TbNaire nairePercentData = naireMapper.getStatisticsData(4L);
		// 获取答卷总的数据统计情况：各选项所占百分比(该数据列表内排除简答题数据)（统计显示）
		List<TbQuestion> answerPercentList = answerRecordMapper.getNaireAnswerPercent(4L);
		// 计算各个选项所占用百分比情况
		for (TbQuestion question :  answerPercentList) {
			double SumSelectCount = 0;
			for (TbOption option : question.getOptionList()) {
				SumSelectCount += option.getSelectCount();
			}
			if (SumSelectCount == 0) {
				SumSelectCount = 1;
			}
			for (TbOption option : question.getOptionList()) {
				String percent = NumberFormatUtil.toPercent(option.getSelectCount()/SumSelectCount);
				option.setSelectPercent(percent);
			}
		}
		nairePercentData.setQuestionList(answerPercentList);
		System.err.println(nairePercentData.getTitle());
		for (TbQuestion question : nairePercentData.getQuestionList()) {
			System.err.println(question.getQuestionDesc());
			for (TbOption option : question.getOptionList()) {
				System.err.println(option.getOptionDesc() + "		" + option.getSelectPercent());
			}
		}


		endTime = System.currentTimeMillis();
		System.err.println("export1 time: " + TimeFormat.formatDuration(endTime - startTime));
		
		
		startTime = System.currentTimeMillis();		
		File dataXlsFile = POIUtil.createNaireDataExcel1(nairePercentData);
		endTime = System.currentTimeMillis();
		System.err.println("export file time: " + TimeFormat.formatDuration(endTime - startTime));
*/

		startTime = System.currentTimeMillis();		
		List<TbRespondentInfo> respondentInfos = answerRecordMapper.getRespondentNaireData(4L);
		
		
		
		int count = 0;
		for(TbRespondentInfo s : respondentInfos) {
			System.err.print(++count + "	" + s.getStartTime() + "	" + s.getEndTime() + "	" + s.getSystem() + "	" + s.getAddress() + "	" + s.getBrowser() + "	" + s.getDuration() + "	" + s.getIp() + "    " + s.getNaire().getId());
			System.err.print("	");
			for (TbQuestion q : s.getNaire().getQuestionList()) {
				System.err.print(q.getQuestionDesc());
				System.err.print("	");
				
				
				String optionDesc = q.getOptionList().get(0).getOptionDesc();
				if (StringUtils.isBlank(optionDesc)) {
					optionDesc = "数据异常";
				}
				System.err.print(optionDesc);
				System.err.print("	");
			}
			System.err.println();
		}
		
		File sDataXlsFile = POIUtil.createNaireDataExcel2(respondentInfos);
		endTime = System.currentTimeMillis();
		
		
		System.err.println("select time: " + TimeFormat.formatDuration(endTime - startTime));
	}
	
	
	@Test
	public void test12() {
		
		
	}


}

