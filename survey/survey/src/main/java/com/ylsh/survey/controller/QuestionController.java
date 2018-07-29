package com.ylsh.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.pojo.TbQuestion;
import com.ylsh.survey.service.QuestionService;


/**
 * @description:问题管理Controller
 * @author ylsh
 * @date 2017年11月30日 下午4:22:42
 */
@Controller
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	
	/**
	 * @description:删除问卷问题
	 * @author ylsh
	 * @date 2017年11月30日 下午4:33:52
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult delete(
			@RequestParam(required=true) Long questionId, 
			@RequestParam(required=true) Long naireId) {
		try {
			questionService.delete(questionId, naireId);
			return ResponseResult.ok();
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error("删除失败");
		}		
	}
	
	/**
	 * @description:更新问卷问题
	 * @author ylsh
	 * @date 2017年11月30日 下午4:34:09
	 * @param question
	 * @return
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult update(@RequestBody TbQuestion question) {
		try {
			ResponseResult result = questionService.update(question);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error("修改失败");
		}
		
	}
	
	/**
	 * @description:新增问卷问题
	 * @author ylsh
	 * @date 2017年11月30日 下午4:34:25
	 * @param question
	 * @return
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult save(@RequestBody TbQuestion question) {
		try {
			ResponseResult result = questionService.save(question);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.error("保存失败");
		}
		
		
	}

}
