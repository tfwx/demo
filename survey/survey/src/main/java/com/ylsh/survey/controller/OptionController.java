package com.ylsh.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.service.OptionService;


/**
 * @description:问卷选项管理Controller
 * @author ylsh
 * @date 2017年11月30日 下午4:21:03
 */
@Controller
@RequestMapping("/option")
public class OptionController {
	
	@Autowired
	private OptionService optionService;
	
	/**
	 * @description:删除问卷选项
	 * @author ylsh
	 * @date 2017年11月30日 下午4:32:46
	 * @param optionId
	 * @param naireId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseResult delete(
			@RequestParam(required=true)Long optionId,
			@RequestParam(required=true)Long naireId){
		try {
			optionService.delete(optionId, naireId);
			return ResponseResult.ok("删除成功");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error("删除失败");
		}
	}
	
	
}
