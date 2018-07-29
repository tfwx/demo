package com.ylsh.survey.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.FontFamily;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ylsh.survey.mapper.TbAnswerRecordMapper;
import com.ylsh.survey.mapper.TbNaireMapper;
import com.ylsh.survey.pojo.TbNaire;
import com.ylsh.survey.pojo.TbOption;
import com.ylsh.survey.pojo.TbQuestion;
import com.ylsh.survey.pojo.TbRespondentInfo;
import com.ylsh.survey.util.NumberFormatUtil;
import com.ylsh.survey.util.POIUtil;
import com.ylsh.survey.util.TimeFormat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/*.xml")
public class ExcelTest {


	@Autowired
	private TbNaireMapper naireMapper;

	@Autowired
	private TbAnswerRecordMapper answerRecordMapper;



	private static XSSFCellStyle cellStyle;
	@Test
	public void test() throws Exception {
		// 获取答题者信息和答卷信息（分条显示）
		List<TbRespondentInfo> respondentInfos = answerRecordMapper.getRespondentNaireData(111L);

		POIUtil.createNaireDataExcel2(respondentInfos);
	}

	@Test	
	public void test2() throws Exception {
		// 获取问卷基本信息
		TbNaire nairePercentData = naireMapper.getStatisticsData(111L);
		// 获取答卷总的数据统计情况：各选项所占百分比(该数据列表内排除简答题数据)（统计显示）
		List<TbQuestion> answerPercentList = answerRecordMapper.getNaireAnswerPercent(111L);
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
		POIUtil.createNaireDataExcel1(nairePercentData);
	}


	public static void main(String[] args) throws Exception {

		File file = new File("src/main/resources/template/mb1.xlsx");
		System.err.println(file.exists());

	}

}
