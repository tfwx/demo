package com.ylsh.survey.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.pojo.TbRespondentInfo;
import com.ylsh.survey.pojo.TbUser;
import com.ylsh.survey.service.NaireService;
import com.ylsh.survey.service.RespondentInfoService;


/**
 * @description: 答题人管理Controller
 * @author ylsh
 * @date 2018年4月25日 下午5:29:39
 */
@Controller
@RequestMapping("/naire/respondent")
public class RespondentInfoController {
	
	@Autowired
	private RespondentInfoService respondentInfoService;
	
	@Autowired
	private NaireService naireService;
	
	
	/**
	 * @description: 查询该问卷的答题者信息
	 * @author ylsh
	 * @date 2018年4月25日 下午5:32:07 
	 * @param naireId
	 * @return
	 */
	@RequestMapping(value="/list", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult listRespondentInfo(
			HttpSession session,
			@RequestParam(value="nid", required=true)Long naireId) {		
		try {
			// 判断问卷所属
			Long userId = ((TbUser) session.getAttribute("user")).getId();
			if(!naireService.isNaireBelongToUser(userId, naireId)) {
				throw new RuntimeException("非法请求");
			}
			// 查询答题者信息
			List<TbRespondentInfo> respondentInfoList = respondentInfoService.listByNaireId(naireId);
			return ResponseResult.ok(respondentInfoList);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}
	
	
	/**
	 * @description: 删除答卷信息（包括答题者信息，答卷数据信息）
	 * @author ylsh
	 * @date 2018年4月25日 下午5:39:29 
	 * @param session
	 * @param respondentInfoId
	 * @return
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult delete(
			HttpSession session,
			@RequestParam(value="nid", required=true)Long naireId,
			@RequestParam(value="rid", required=true)Long respondentId) {
		try {
			// 判断问卷所属
			Long userId = ((TbUser) session.getAttribute("user")).getId();
			if(!naireService.isNaireBelongToUser(userId, naireId)) {
				throw new RuntimeException("非法请求");
			}
			respondentInfoService.delete(naireId, respondentId);
			return ResponseResult.ok("删除成功");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}
	
	

}
