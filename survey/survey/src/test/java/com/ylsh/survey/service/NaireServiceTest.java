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
import com.ylsh.survey.util.TimeFormat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class NaireServiceTest {

	@Autowired
	private NaireService naireService;

	@Autowired
	private TbBriefAnswerMapper tbBriefAnswerMapper;

	@Autowired
	private TbNaireMapper naireMapper;


	@Autowired
	private TbProvinceMapper provinceMapper;


	@Autowired
	private TbRespondentInfoMapper respondentInfoMapper;


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




	/*@Test
	public void testGetNaireListLazyLoading() {		
		List<TbNaire> naireList = naireService.getNaireListByUserId(1L, 0, 6);
		for (TbNaire naire : naireList) {
			System.out.println(naire.getTitle());			
			System.out.println("分类：" + naire.getCategory().getCategoryDesc());
			int count = 0;
			for (TbQuestion question: naire.getQuestionList()) {
				System.out.println(++count + ". " + question.getQuestionDesc());
				System.out.println("分类：" + question.getCategory().getCategoryDesc());
				char index = 'A';
				for (TbOption option : question.getOptionList()) {
					System.out.println((index++) + ". " + option.getOptionDesc());
				}
			}
		}
	}*/

	@Test
	public void testAddNaire() {
		TbNaire naire = new TbNaire();
		naire.setTitle("员工满意度调查");
		naire.setNaireDesc("员工满意度调查");
		naire.setCreateTime(new Date());
		naire.setEndTime(new Date());
		naire.setUserId(1L);
		naire.setStatus((byte) 4);
		naire.setCategoryId((byte) 1);
		naire.setCollectCount(0L);


		TbQuestion question = new TbQuestion();
		question.setQuestionDesc("你对工作满意么？");
		question.setCategoryId((byte) 1);

		TbOption option1 = new TbOption();
		option1.setOptionDesc("满意");
		TbOption option2 = new TbOption();
		option2.setOptionDesc("不满意");


		naire.getQuestionList().add(question);
		question.getOptionList().add(option1);
		question.getOptionList().add(option2);


		naireService.save(naire);

	}


	@Test
	public void testGetNaire() {
		System.err.println(naireService.get(108L).getCollectCount());
	}


	@Test
	public void testJson(){
		ObjectMapper mapper = new ObjectMapper();

		try {
			String s = mapper.writeValueAsString(naireService.get(1L));
			System.err.println(s);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test7() throws FileNotFoundException {
		List<String> list2 = new ArrayList<String>(2000);
		File file = new File("C:\\Users\\ylsh\\Desktop\\123.txt");
		Scanner scan = new Scanner(file);
		String str = null;
		str = scan.nextLine();
		int count = 0;
		while (StringUtils.isNotBlank(str)) {
			System.err.println(str);
			list2.add(str);
			count++;
			if (count > 1801) {
				break;
			}
			str = scan.nextLine();
		}

		scan.close();

		Random rand = new Random();
		//	Date date = new Date();
		List<TbRespondentInfo> list = respondentInfoMapper.selectByExample(null);
		for (TbRespondentInfo r : list) {
			/*long time = (50 + (int)(Math.random()*10) + (int)(Math.random()*100)) * 1000;
			date.setTime(r.getStartTime().getTime() + time);
			r.setEndTime(date);
			r.setDuration(TimeFormat.formatDuration(r.getStartTime(), r.getEndTime()));*/

			/*if (Math.random() < 0.6) {
				r.setSystem("Android");
			} else if (Math.random() < 0.9) {
				r.setSystem("Iphone");
			} else {
				continue;
			}*/

			//	String ip = (rand.nextInt(254) + 1) + "." + (rand.nextInt(254) + 1)+ "." + (rand.nextInt(254) + 1)+ "." + (rand.nextInt(254) + 1);
			r.setIp(list2.get(rand.nextInt(1800)+1));
			respondentInfoMapper.updateByPrimaryKey(r);

		}
		/*
		TbRespondentInfoExample example = new TbRespondentInfoExample();
		TbRespondentInfoExample.Criteria criteria = example.createCriteria();
		criteria.andIdNotEqualTo(0L);

		TbRespondentInfo record = new TbRespondentInfo();
		Date date = new Date();
		record.setStartTime(date);
		record.setEndTime(date);
		respondentInfoMapper.updateByExampleSelective(record, example);*/

		/*NumberFormat numberFormat = NumberFormat.getPercentInstance();
		numberFormat.setMaximumFractionDigits(2);
		System.err.println(numberFormat.format(12/12.0));*/
	}


	@Test
	public void testCreateExcel() {
		Map<String, TbNaire> dataMap = new HashMap<String, TbNaire>();
		TbNaire naire = naireService.get(4L);

		NumberFormat numberFormat = NumberFormat.getPercentInstance();
		numberFormat.setMaximumFractionDigits(2);

		for (TbQuestion question: naire.getQuestionList()) {
			if (question.getCategoryId() == 3) {
				TbBriefAnswerExample example = new TbBriefAnswerExample();
				TbBriefAnswerExample.Criteria criteria = example.createCriteria();
				criteria.andQuestionIdEqualTo(question.getId());
				List<TbBriefAnswer> answerList = tbBriefAnswerMapper.selectByExample(example);
				List<TbOption> optionList = new ArrayList<TbOption>();
				for (TbBriefAnswer answer : answerList) {
					TbOption option = new TbOption();
					option.setOptionDesc(answer.getAnswerContent());
					optionList.add(option);
				}
				question.setOptionList(optionList);
			} else {
				float allCount = 0;
				for (TbOption option : question.getOptionList()) {
					allCount += option.getSelectCount();
				}
				for (TbOption option : question.getOptionList()) {
					float per = option.getSelectCount()/allCount;
					option.setOptionDesc(option.getOptionDesc() + "percent" + numberFormat.format(per));
				}


			}
		}


		dataMap.put("naire", naire);
		//WordUtil.createDoc(dataMap, WordUtil.TEMPLATE_DATA);


		/*System.out.println(naire.getTitle());
		int count = 0;
		for (TbQuestion question: naire.getQuestionList()) {

			if (question.getCategoryId() == 3) {
				TbBriefAnswerExample example = new TbBriefAnswerExample();
				TbBriefAnswerExample.Criteria criteria = example.createCriteria();
				criteria.andQuestionIdEqualTo(question.getId());
				List<TbBriefAnswer> answerList = tbBriefAnswerMapper.selectByExample(example);
				List<TbOption> optionList = new ArrayList<TbOption>();
				for (TbBriefAnswer answer : answerList) {
					TbOption option = new TbOption();
					option.setOptionDesc(answer.getAnswerContent());
					optionList.add(option);
				}
				question.setOptionList(optionList);

			}


			System.out.println(++count + ". " + question.getQuestionDesc());
			char index = 'A';
			for (TbOption option : question.getOptionList()) {
				System.out.println((index++) + ". " + option.getOptionDesc());
			}
		}*/
	}



	@Test
	public void test12() {
		long startTime = System.currentTimeMillis();


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









		List<TbRespondentInfo> respondentInfos = answerRecordMapper.getRespondentNaireData(4L);
		for(TbRespondentInfo s : respondentInfos) {
			for (TbQuestion q : s.getNaire().getQuestionList()) {
				for (TbOption o : q.getOptionList()) {
					System.err.println(o.getOptionDesc());
				}

			}


		}
		long  endtTime = System.currentTimeMillis();

		System.err.println(TimeFormat.formatDuration(endtTime - startTime));
	}


}

