package com.ylsh.survey.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.ylsh.survey.dto.NaireAnswerDataRecord;
import com.ylsh.survey.pojo.TbRespondentInfo;

public interface RespondentInfoService {
	
	List<TbRespondentInfo> listByNaireId(Long naireId);
	
	int delete(Long naireId, Long respondentId);
	
	long save(HttpServletRequest request, NaireAnswerDataRecord answerDataRecord);
	
	TbRespondentInfo get(Long id);
	
	void deleteByNaireId(long naireId);

}
