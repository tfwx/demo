package com.ylsh.survey.dto;

import java.util.Date;
import java.util.List;
import com.ylsh.survey.pojo.TbBriefAnswer;
import com.ylsh.survey.pojo.TbOption;


/**
 * @description: 答卷相关信息记录实体类
 * @author ylsh
 * @date 2018年4月25日 下午6:58:31
 */
public class NaireAnswerDataRecord {
	
	private Date startTime;							// 答题起始时间
	
	private Long naireId;							// 问卷ID
	
	private String ip;								// 答题者IP地址
	
	private Long respondentInfoId;					// 答题者ID
	
	private String cityCode;						// 答题者所在行政区划编码
	
	private List<TbOption> optionAnswerList;		// 选择题数据（content: optionId）
	
	private List<TbBriefAnswer> briefAnswerList;	// 简答题数据（key:questionId value:content）

	
	public NaireAnswerDataRecord() {}


	public Long getNaireId() {
		return naireId;
	}


	public void setNaireId(Long naireId) {
		this.naireId = naireId;
	}


	public List<TbOption> getOptionAnswerList() {
		return optionAnswerList;
	}


	public void setOptionAnswerList(List<TbOption> optionAnswerList) {
		this.optionAnswerList = optionAnswerList;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Long getRespondentInfoId() {
		return respondentInfoId;
	}

	public void setRespondentInfoId(Long respondentInfoId) {
		this.respondentInfoId = respondentInfoId;
	}

	public List<TbBriefAnswer> getBriefAnswerList() {
		return briefAnswerList;
	}

	public void setBriefAnswerList(List<TbBriefAnswer> briefAnswerList) {
		this.briefAnswerList = briefAnswerList;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		startTime.setTime(startTime.getTime()/1000*1000);
		this.startTime = startTime;
	}
}
