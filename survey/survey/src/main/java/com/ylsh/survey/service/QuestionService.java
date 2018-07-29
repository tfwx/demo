package com.ylsh.survey.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.pojo.TbQuestion;


/**
 * @description:问卷问题Service接口
 * @author ylsh
 * @date 2017年11月30日 下午4:54:12
 */
public interface QuestionService {
	
	void delete(Long questionId, Long naireId);
	
	ResponseResult update(TbQuestion question);

	ResponseResult save(TbQuestion question) throws JsonProcessingException;

}
