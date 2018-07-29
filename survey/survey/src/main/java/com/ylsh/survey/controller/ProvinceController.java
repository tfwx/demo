package com.ylsh.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.service.ProvinceService;


/**
 * @description: 地区记录管理Controller
 * @author ylsh
 * @date 2018年4月25日 下午5:28:37
 */
@Controller
@RequestMapping("/naire/area")
public class ProvinceController {
	
	@Autowired
	private ProvinceService provinceService;
	
	/**
	 * @description: 查询问卷数据来源分布取情况
	 * @author ylsh
	 * @date 2018年4月18日 下午9:11:24 
	 * @param naireId
	 * @return
	 */
	@RequestMapping(value="/statistics", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult getNaireAreaData(@RequestParam(required=true, value="nid") Long naireId) {
		try {
			return ResponseResult.ok(provinceService.getNaireAreaData(naireId));
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}

}
