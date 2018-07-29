package com.ylsh.survey.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylsh.survey.dto.NaireAnswerDataRecord;
import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.pojo.TbQuestion;
import com.ylsh.survey.pojo.TbRespondentInfo;
import com.ylsh.survey.pojo.TbUser;
import com.ylsh.survey.service.AnswerRecordService;
import com.ylsh.survey.service.NaireService;
import com.ylsh.survey.service.RespondentInfoService;


/**
 * @description: 问卷答案管理Controller
 * @author ylsh
 * @date 2018年4月25日 下午4:25:03
 */
@Controller
@RequestMapping("/naire/answer")
public class AnswerRecordController {
	
	@Autowired
	private AnswerRecordService answerRecordService;
	
	@Autowired
	private RespondentInfoService respondentInfoService;
	
	@Autowired
	private NaireService naireService;
	
	
	
	/**
	 * @description: 记录答题者提交的问卷答案
	 * @author ylsh
	 * @date 2018年4月25日 下午4:25:31 
	 * @param record
	 * @param respondentInfo
	 * @return
	 */
	@RequestMapping(value="/record", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult record(
			HttpSession session,
			HttpServletRequest resuest,
			@RequestBody NaireAnswerDataRecord answerDataRecord) {
		try {
			// 保存答卷信息
			answerRecordService.record(resuest, answerDataRecord);
			session.removeAttribute("startTime");
			return ResponseResult.ok("保存成功");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
		
	}
	
	
	/**
	 * @description: 查询答卷的详细信息（包含答题者的信息）
	 * @author ylsh
	 * @date 2018年4月25日 下午4:38:21 
	 * @param model
	 * @param naireId
	 * @param respondentId
	 * @return
	 */
	@RequestMapping(value="/view")
	public String getDetailInfo(
			Model model,
			HttpSession session,
			@RequestParam(value="index", required=true)Long index, 
			@RequestParam(value="nid", required=true)Long naireId, 
			@RequestParam(value="rid", required=true)Long respondentId) {
		try {
			// 判断问卷是否属于当前用户
			Long userId = ((TbUser) session.getAttribute("user")).getId();
			if (!naireService.isNaireBelongToUser(userId, naireId)) {
				throw new RuntimeException("非法操作");
			}
			// 查询答题者信息
			TbRespondentInfo respondentInfo = respondentInfoService.get(respondentId);
			// 查询答卷信息
			List<TbQuestion> answerList = answerRecordService.getDetailInfo(naireId, respondentId);
			// 返回数据
			model.addAttribute("index", index);
			model.addAttribute("respondentInfo", respondentInfo);
			model.addAttribute("answerList", answerList);
		} catch(Exception e) {
			e.printStackTrace();
			return "error/404";
		}
		
		return "singleAnsDetail";
	}
	
	
	/**
	 * @description: 获取问卷答案统计图表数据（只包含选择题）
	 * @author ylsh
	 * @date 2018年4月26日 上午11:58:28 
	 * @param naireId
	 * @return
	 */
	@RequestMapping(value="/chart", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult chart(
			HttpSession session,
			@RequestParam(value="nid", required=true)Long naireId) {
		try {
			long userId = ((TbUser) session.getAttribute("user")).getId();
			if (!naireService.isNaireBelongToUser(userId, naireId)) {
				throw new RuntimeException("非法操作");
			}
			List<TbQuestion> answerStatisticsList = answerRecordService.chart(naireId);
			return ResponseResult.ok(answerStatisticsList);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}
	
	/**
	 * @description: 重置问卷已收集的数据（包括：答题人信息，答题数据信息：选择和简答数据）
	 * @author ylsh
	 * @date 2018年4月5日 下午1:07:01
	 * @param session
	 * @param naireId
	 * @return
	 */
	@RequestMapping(value="/reset", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult reset(
			HttpSession session, 
			@RequestParam(value="nid", required=true)Long naireId) {
		try {
			Long userId = ((TbUser) session.getAttribute("user")).getId();
			if (!naireService.isNaireBelongToUser(userId, naireId)) {
				throw new RuntimeException("非法操作");
			}
			answerRecordService.reset(naireId);
			return ResponseResult.ok("重置成功");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}
	
	/**
	 * @description: 下载问卷统计的数据
	 * @author ylsh
	 * @date 2018年1月1日 下午7:05:34 
	 * @param response
	 * @param session
	 * @param naireId
	 * @throws IOException 
	 */
	@RequestMapping(value="/download/data/{id}")
	public void export(HttpServletResponse response, HttpSession session, 
			@PathVariable("id")Long naireId) throws IOException {
		try {
			// 验证问卷所属
			Long userId = ((TbUser) session.getAttribute("user")).getId();
			if (!naireService.isNaireBelongToUser(userId, naireId)) {
				throw new RuntimeException("非法操作");
			}
			answerRecordService.export(response, naireId);
		} catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("error/404");
		}		
	}

}
