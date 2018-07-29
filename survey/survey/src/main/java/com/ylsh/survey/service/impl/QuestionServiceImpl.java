package com.ylsh.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.mapper.TbOptionMapper;
import com.ylsh.survey.mapper.TbQuestionMapper;
import com.ylsh.survey.pojo.TbOption;
import com.ylsh.survey.pojo.TbQuestion;
import com.ylsh.survey.service.QuestionService;


/**
 * @description:问题管理Service
 * @author ylsh
 * @date 2017年11月30日 下午4:56:26
 */
@Transactional
@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private TbQuestionMapper questionMapper;

	@Autowired
	private TbOptionMapper optionMapper;


	/**
	 * @description:删除问题
	 * @author ylsh
	 * @date 2017年11月30日 下午4:56:49
	 * @param questionId
	 * @param naireId
	 * @see com.ylsh.survey.service.QuestionService#delete(java.lang.Long, java.lang.Long)
	 */
	@Override
	@CacheEvict(value="NaireCache", key="'naire' + #naireId")
	public void delete(Long questionId, Long naireId) {
		// 根据主键ID删除问题，其下选项会级联删除
		questionMapper.deleteByPrimaryKey(questionId);
	}


	/**
	 * @description:修改问题
	 * @author ylsh
	 * @date 2017年11月30日 下午4:57:02
	 * @param question
	 * @return
	 * @see com.ylsh.survey.service.QuestionService#update(com.ylsh.survey.pojo.TbQuestion)
	 */
	@Override
	@CacheEvict(value="NaireCache", key="'naire' + #question.naireId")
	public ResponseResult update(TbQuestion question) {
		boolean insertFlag = false;

		questionMapper.updateByPrimaryKeySelective(question);
		for (TbOption option : question.getOptionList()) {
			if (option.getId() != null) {	// 有ID什么该选项为已存在选项
				// 更新选项
				optionMapper.updateByPrimaryKeySelective(option);
			} else {						// 无ID时新增选项
				option.setSelectCount(0L);
				optionMapper.insert(option);
				insertFlag = true;
			}
		}

		ResponseResult result = ResponseResult.ok();
		// 若在编辑问题时，新增加了选项，将问题返回（界面更新选项ID）
		if (insertFlag) {
			result.setMsg(question);
		}
		return result;
	}

	/**
	 * @description:新增问题
	 * @author ylsh
	 * @date 2017年11月30日 下午4:57:12
	 * @param question
	 * @return
	 * @throws JsonProcessingException 
	 * @see com.ylsh.survey.service.QuestionService#saveQuestion(com.ylsh.survey.pojo.TbQuestion)
	 */
	@Override
	@CacheEvict(value="NaireCache", key="'naire' + #question.naireId")
	public ResponseResult save(TbQuestion question) throws JsonProcessingException {
		// 插入问题
		questionMapper.insert(question);
		// 该问题有可能是简答题, 简答题没有选项
		if (question.getCategoryId() != 3) {
			// 补全父ID
			for (TbOption option : question.getOptionList()) {
				option.setQuestionId(question.getId());
			}
			// 批量插入选项
			optionMapper.batchInsert(question.getOptionList());
		}

		// 将新插入的问题转为json返回
		question = questionMapper.selectByPrimaryKey(question.getId());
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(question);
		return ResponseResult.ok(jsonStr);
	}

}
