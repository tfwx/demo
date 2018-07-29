package com.ylsh.survey.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ylsh.survey.pojo.TbNaireCategory;
import com.ylsh.survey.pojo.TbUser;
import com.ylsh.survey.service.AnswerRecordService;
import com.ylsh.survey.service.NaireCategoryService;
import com.ylsh.survey.util.RequestUtil;


/**
 * @description:页面展示Controller
 * @author ylsh
 * @date 2017年11月30日 下午4:21:27
 */
@Controller
public class PageController {

	@Autowired
	private NaireCategoryService naireCategoryService;

	@Autowired
	private AnswerRecordService answerRecordService;

	/**
	 * @description:显示主页
	 * @author ylsh
	 * @date 2017年11月30日 下午4:33:07
	 * @return
	 */
	@RequestMapping("/")
	public String showIndexPage(Model model, HttpServletRequest request) {
		// 首页显示收集总数据数
		Map<String, Long> map = answerRecordService.getIndexStatistics();
		model.addAttribute("statisticMap", map);
		// 查询问卷分类
		List<TbNaireCategory> naireCategoryList = naireCategoryService.getAll();
		model.addAttribute("naireCategoryList", naireCategoryList);
		
		System.err.println("real ip: " + RequestUtil.getIpAddr(request));
		return "index";
	}

	/**
	 * @description:自动匹配页面
	 * @author ylsh
	 * @date 2017年11月30日 下午4:33:24
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/{page}")
	public String showPage(@PathVariable("page")String page, Model model, Test test) {
		System.err.println("p|" + test.getP1() + "|");
		System.err.println("p|" + test.getP2() + "|");
		/*System.err.println(user.getId() + "" + user.getNick());*/
		/*if ("sss".equals(page)) {
			TbUser u = new TbUser();
			u.setId(1L);
			u.setNick("ylsh");
			u.setPassword("12345");
			model.addAttribute("user", u);
			u.setPhone("2");
			List<TbUser> userList = new ArrayList<TbUser>();
			userList.add(u);
			u = new TbUser();
			u.setId(2L);
			u.setNick("dwmx");
			userList.add(u);
			u = new TbUser();
			u.setId(3L);
			u.setNick("msmw");
			userList.add(u);
			model.addAttribute("users", userList);
		}*/
		return page;
	}
	
	
	@ModelAttribute("user")
	public TbUser get() {
		return new TbUser();
	}



}


class Test {
	private String p1;
	private String p2;
	public String getP1() {
		return p1;
	}
	public void setP1(String p1) {
		this.p1 = p1;
	}
	public String getP2() {
		return p2;
	}
	public void setP2(String p2) {
		this.p2 = p2;
	}
}