package com.ylsh.survey.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ylsh.survey.dto.NaireAnswerDataRecord;
import com.ylsh.survey.pojo.TbQuestion;


public interface AnswerRecordService {
	
	List<TbQuestion> getDetailInfo(Long naireId, Long respondentId);
	
	void record(HttpServletRequest resuest, NaireAnswerDataRecord answerDataRecord);
	
	Map<String, Long> getIndexStatistics();

	List<TbQuestion> chart(Long naireId);
	
	void reset(Long naireId);

	void export(HttpServletResponse response, Long naireId);

}
