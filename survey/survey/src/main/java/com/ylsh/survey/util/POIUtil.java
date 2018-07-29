package com.ylsh.survey.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import com.ylsh.survey.pojo.TbNaire;
import com.ylsh.survey.pojo.TbOption;
import com.ylsh.survey.pojo.TbQuestion;
import com.ylsh.survey.pojo.TbRespondentInfo;


/**
 * @description: POI处理WORD、EXCEL工具类
 * @author ylsh
 * @date 2018年4月29日 下午1:10:49
 */
public class POIUtil {
	
	
	public static final String TEMP_FILE_PATH = "D:/wenjuanwang/temp/";

	/**
	 * 题目类型
	 */
	private static String[] qType = new String[] {"", "【单选题】", "【多选题】", "【简答题】"};

	/**
	 * @description: 读取word文档内容
	 * @author ylsh
	 * @date 2018年4月29日 下午1:14:34 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String readWord(String path) throws Exception {
		return readWord(new File(path));
	}

	/**
	 * @description: 读取word文档内容
	 * @author ylsh
	 * @date 2018年4月29日 下午1:11:08 
	 * @param path
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static String readWord(File file) throws Exception {
		if (file == null || !file.exists()) {
			throw new FileNotFoundException();
		}
		String s = "";
		String fileName = file.getName();

		if(fileName.endsWith(".doc")) {
			InputStream is = new FileInputStream(file);
			HWPFDocument  ex = new HWPFDocument(is);
			s = ex.getDocumentText();
			if (is != null) {
				is.close();
			}
		} else if (fileName.endsWith(".docx")) {
			InputStream is = new FileInputStream(file);
			XWPFDocument doc = new XWPFDocument(is);  
			List<XWPFParagraph> paras = doc.getParagraphs();  
			StringBuffer buffer = new StringBuffer();
			for (XWPFParagraph para : paras) {
				buffer.append(para.getText() + "\n");
			}  
			s = buffer.toString();
			/*OPCPackage opcPackage = POIXMLDocument.openPackage(file.getAbsolutePath());
			POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
			s = extractor.getText();*/
		} else {
			throw new Exception("word文档格式错误");
		}

		return s;
	}




	/**
	 * @description: 创建Word文档段落
	 * @author ylsh
	 * @date 2018年4月30日 上午12:04:38 
	 * @param document
	 * @param alignment
	 * @param fontSize
	 * @param text
	 * @param carriages
	 */
	private static void createParagraph(XWPFDocument document, ParagraphAlignment alignment, int fontSize, String text, int carriages) {
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(alignment);
		XWPFRun paragraphProperties = paragraph.createRun();
		paragraphProperties.setFontSize(fontSize);
		paragraphProperties.setText(text);

		// setFontFamily 对中文无效，需采用以下方式
		CTRPr rpr = paragraphProperties.getCTR().isSetRPr() ? paragraphProperties.getCTR().getRPr() : paragraphProperties.getCTR().addNewRPr();
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("微软雅黑");
		fonts.setEastAsia("微软雅黑");
		fonts.setHAnsi("微软雅黑");

		for (int i = 0; i < carriages; i++) {
			paragraphProperties.addCarriageReturn();
		}
	}


	/**
	 * @description: 根据问卷文档（Word）
	 * @author ylsh
	 * @date 2018年4月30日 上午12:01:37 
	 * @param naire
	 * @throws Exception
	 */
	public static File createNaireDocument(TbNaire naire) throws Exception { 
		long start = System.currentTimeMillis();

		// 创建Document
		XWPFDocument document = new XWPFDocument(); 

		createParagraph(document, ParagraphAlignment.CENTER, 18, naire.getTitle(), 1);
		createParagraph(document, ParagraphAlignment.LEFT, 12, naire.getNaireDesc(), 1);

		List<TbQuestion> questionList = naire.getQuestionList();
		for (int i = 0; i < questionList.size(); i++) {
			TbQuestion question = questionList.get(i);
			String quedtionDesc = (i+1)+ "  " + question.getQuestionDesc() + qType[question.getCategoryId()];			
			createParagraph(document, ParagraphAlignment.LEFT, 12, quedtionDesc, 0);

			if (question.getCategoryId() == 3) {
				createParagraph(document, ParagraphAlignment.LEFT, 12, "", 2);
			} else {
				List<TbOption> optionList = question.getOptionList();
				for (int j = 0; j < optionList.size(); j++) {
					TbOption option = optionList.get(j);
					String oText = "    " + (char)(j+65) + "  " + option.getOptionDesc();
					int carriages = (j==optionList.size()-1) ? 1: 0;
					createParagraph(document, ParagraphAlignment.LEFT, 12, oText, carriages);
				}
			}
		}

		String fileName = naire.getTitle() + System.currentTimeMillis() + ".docx";
		File file = new File(TEMP_FILE_PATH + fileName);
		file.mkdirs();
		if (file.exists()) {
			file.delete();
		}		
		OutputStream outputStream = new FileOutputStream(file);  
		document.write(outputStream);  
		outputStream.close();

		long end = System.currentTimeMillis();
		System.err.println("【" + naire.getTitle() + "】文档生成时间：" + (end -start));

		return file;
	} 

	/**
	 * @description: 生成问卷数据报表（Excel）
	 * @author ylsh
	 * @date 2018年4月30日 上午12:12:33 
	 * @param naire
	 * @return
	 * @throws Exception 
	 */
	public static File createNaireDataExcel1(TbNaire naire) throws Exception {
		//Tomcat8默认访问路径为：C:\Windows\System32(原因未知)，这里通过绝对路径访问模板文件
		/*String templateFileName = URLDecoder.decode(POIUtil.class.getClassLoader()
                .getResource("/").getPath()+"template/mb1.xlsx", "utf-8");*/
		FileInputStream fis = new FileInputStream("src/main/resources/template/mb1.xlsx");
		//根据模板创建excel工作簿
		XSSFWorkbook workBook = new XSSFWorkbook(fis);		
		//获取创建的工作簿第一页
		XSSFSheet sheet = workBook.getSheetAt(0);


		// 单元格样式
		XSSFFont font = workBook.createFont();
		font.setFontName("Microsoft YaHei UI");
		XSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		sheet.getRow(0).getCell(0).setCellValue(naire.getTitle());

		int rowNum = 0;

		List<TbQuestion> questionList = naire.getQuestionList();
		for(int i = 0; i < questionList.size(); i++) {
			TbQuestion question = questionList.get(i);
			rowNum++;
			setCellValue(sheet, rowNum, 0, "Q"+ (i+1), cellStyle);
			setCellValue(sheet, rowNum, 1, question.getQuestionDesc(), cellStyle);

			List<TbOption> optionList = question.getOptionList();
			for (int j = 0; j < optionList.size(); j++) {
				TbOption option = optionList.get(j);
				rowNum++;
				setCellValue(sheet, rowNum, 1, (char)(j+65) + "  " + option.getOptionDesc(), cellStyle);
				setCellValue(sheet, rowNum, 2, option.getSelectCount()+"", cellStyle);
				setCellValue(sheet, rowNum, 3, option.getSelectPercent(), cellStyle);
			}

		}
		//创建Excel文件输出流对象
		File file = new File(TEMP_FILE_PATH + naire.getTitle() + System.currentTimeMillis() + ".xlsx");		
		if (file.exists()) {
			file.delete();
		}
		FileOutputStream fos = new FileOutputStream(file);
		workBook.write(fos);
		fis.close();
		fos.close();
		return file;
	}


	/**
	 * @description: 生成答卷分条数据报表
	 * @author ylsh
	 * @date 2018年4月30日 下午4:19:21 
	 * @param respondentList
	 * @return
	 * @throws Exception
	 */
	public static File createNaireDataExcel2(List<TbRespondentInfo> respondentList) throws Exception {
		//根据模板创建excel工作簿
		XSSFWorkbook workBook = new XSSFWorkbook();
		//获取创建的工作簿第一页
		XSSFSheet sheet = workBook.createSheet();

		XSSFFont font = workBook.createFont();
		font.setFontName("Microsoft YaHei UI");
		XSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		sheet.setDefaultColumnWidth(13);		

		int cellNum = 0;
		setCellValue(sheet, 0, cellNum++, "序号", cellStyle);
		setCellValue(sheet, 0, cellNum++, "IP", cellStyle);
		setCellValue(sheet, 0, cellNum++, "浏览器", cellStyle);
		setCellValue(sheet, 0, cellNum++, "系统", cellStyle);
		setCellValue(sheet, 0, cellNum++, "区域", cellStyle);
		setCellValue(sheet, 0, cellNum++, "开始时间", cellStyle);
		setCellValue(sheet, 0, cellNum++, "结束时间", cellStyle);
		setCellValue(sheet, 0, cellNum++, "答题时长", cellStyle);

		// 设置表头
		List<TbQuestion> questionList = respondentList.get(0).getNaire().getQuestionList();
		for (int i = 0; i < questionList.size(); i++) {
			TbQuestion question = questionList.get(i);
			String questionDesc = (i+1) + "  " + question.getQuestionDesc() + qType[question.getCategoryId()];
			setCellValue(sheet, 0, cellNum++, questionDesc, cellStyle);
		}

		for (int i = 0; i < respondentList.size(); i++) {
			TbRespondentInfo respondent = respondentList.get(i);
			int rowNum = i + 1;
			cellNum = 0;

			setCellValue(sheet, rowNum, cellNum++, rowNum + "", cellStyle);
			setCellValue(sheet, rowNum, cellNum++, respondent.getIp(), cellStyle);
			setCellValue(sheet, rowNum, cellNum++, respondent.getBrowser(), cellStyle);
			setCellValue(sheet, rowNum, cellNum++, respondent.getSystem(), cellStyle);
			setCellValue(sheet, rowNum, cellNum++, respondent.getAddress(), cellStyle);
			setCellValue(sheet, rowNum, cellNum++, TimeFormat.formatDate(respondent.getStartTime()), cellStyle);
			setCellValue(sheet, rowNum, cellNum++, TimeFormat.formatDate(respondent.getEndTime()), cellStyle);
			setCellValue(sheet, rowNum, cellNum++, respondent.getDuration(), cellStyle);

		
			for (TbQuestion question : respondent.getNaire().getQuestionList()) {
				String optionDesc = question.getOptionList().get(0).getOptionDesc();
				if (StringUtils.isBlank(optionDesc)) {
					optionDesc = "数据缺失";
			//		cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
					
				} else {
			//		cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
				}
				setCellValue(sheet, rowNum, cellNum++, optionDesc, cellStyle);	
			}
		}

		sheet.setDefaultRowHeightInPoints(20);
		sheet.getRow(0).setHeight((short) 400);
		File file = new File(TEMP_FILE_PATH + System.currentTimeMillis() + ".xlsx");
		file.mkdirs();
		if (file.exists()) {
			file.delete();
		}
		//创建Excel文件输出流对象
		FileOutputStream fos = new FileOutputStream(file);
		workBook.write(fos);
		fos.close();
		return file;
	}


	/**
	 * @description: 设置单元格内容
	 * @author ylsh
	 * @date 2018年4月30日 下午12:41:15 
	 * @param sheet
	 * @param rowNum
	 * @param cellNum
	 * @param value
	 */
	private static void setCellValue(XSSFSheet sheet, int rowNum, int cellNum, String value, XSSFCellStyle cellStyle) {
		XSSFRow row = sheet.getRow(rowNum);
		if (row == null) {
			row = sheet.createRow(rowNum);
		}
		XSSFCell cell = row.createCell(cellNum);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}




}
