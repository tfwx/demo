package com.ylsh.survey.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.pojo.TbNaire;
import com.ylsh.survey.pojo.TbNaireCategory;
import com.ylsh.survey.pojo.TbUser;
import com.ylsh.survey.service.NaireCategoryService;
import com.ylsh.survey.service.NaireService;
import com.ylsh.survey.util.JSONUtil;
import com.ylsh.survey.util.RequestUtil;

/**
 * @description:问卷管理Controller
 * @author ylsh
 * @date 2017年11月30日 下午4:20:21
 */
@Controller
@RequestMapping("/naire")
public class NaireController {

	@Autowired
	private NaireService naireService;
	
	@Autowired
	private NaireCategoryService naireCategoryService;
	
	
	
	/**
	 * @description:问卷分页查询
	 * @author ylsh
	 * @date 2017年11月30日 下午4:23:48
	 * @param session
	 * @param model
	 * @param queryString
	 * @return
	 */
	@RequestMapping(value="/list")
	public String showNaireList(
			HttpSession session, 
			Model model, 
			HttpServletRequest request) {
		
		Map<String, Object> paramMap = RequestUtil.getRequestParam(request);
		long userId = ((TbUser)session.getAttribute("user")).getId();
		paramMap.put("userId", userId);
		
		// 查询列表
		PageInfo<TbNaire> pageInfo = naireService.list(paramMap);
		model.addAttribute("pageInfo", pageInfo);

		// 查询问卷分类
		List<TbNaireCategory> categoryList = naireCategoryService.getAll();
		model.addAttribute("naireCategoryList", categoryList);
		/*
		String paramJson = JSONUtil.toJSON(paramMap);
		model.addAttribute("queryParameter", paramMap);
		model.addAttribute("queryParameterJson", paramJson);
		*/
		return "list";
	}

	/**
	 * @description:编辑问卷页面
	 * @author ylsh
	 * @date 2017年11月30日 下午4:25:58
	 * @param model
	 * @param naireId
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String editNaire(Model model, @PathVariable("id")Long naireId) {
		TbNaire naire = naireService.get(naireId);
		ObjectMapper mapper = new ObjectMapper();
		String naireJson = null;
		try {
			naireJson = mapper.writeValueAsString(naire);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		Map<String, TbNaireCategory> categoryMap = naireCategoryService.getAllAsMap();
		model.addAttribute("naireCategoryMap", categoryMap);
		model.addAttribute("naireData", JSONUtil.fixJsonStr(naireJson));
		model.addAttribute("parentCategory", naire.getCategory().getParentNode().getId());
		model.addAttribute("childCategory", naire.getCategory().getId());
		model.addAttribute("isEditNaire", 1);
		return "edit";
	}
	

	/**
	 * @description:新建问卷页面
	 * @author ylsh
	 * @date 2017年11月30日 下午4:26:45
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String editNaire(
			Model model, 
			@RequestParam(name="p", required=true) Byte parentCategory,
			@RequestParam(name="c", required=false) Byte childCategory) {
		model.addAttribute("parentCategory", parentCategory);
		if (childCategory != null) {
			model.addAttribute("childCategory", childCategory);
		}
		Map<String, TbNaireCategory> categoryMap = naireCategoryService.getAllAsMap();
		model.addAttribute("naireCategoryMap", categoryMap);
		model.addAttribute("isEditNaire", 0);
		return "edit";
	}
	

	/**
	 * @description: 根据生成问卷页面传过来的数据跳转至编辑页面，自动生成问卷
	 * @author ylsh
	 * @date 2018年4月8日 上午9:50:33 
	 * @param model
	 * @param naireJson
	 * @return
	 */
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createNaire(Model model, 
			@RequestParam(required=true, name="category")String category,
			@RequestParam(required=true, name="naireJson")String naireJson) {
		Map<String, TbNaireCategory> categoryMap = naireCategoryService.getAllAsMap();
		if (!categoryMap.containsKey(category)) {
			return "error/404";
		}
		model.addAttribute("naireCategoryMap", categoryMap);
		model.addAttribute("parentCategory", categoryMap.get(category).getParentId().toString());
		model.addAttribute("childCategory", Byte.parseByte(category));
		model.addAttribute("naireData", naireJson);
		model.addAttribute("isEditNaire", 0);
		return "edit";
	}
	
	

	/**
	 * @description:更新问卷
	 * @author ylsh
	 * @date 2017年11月30日 下午4:27:12
	 * @param naire
	 * @return
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult update(TbNaire naire) {
		try {
			naireService.update(naire);
			return ResponseResult.ok("更新成功");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error("更新失败");
		}
	}

	/**
	 * @description:删除问卷(彻底删除)
	 * @author ylsh
	 * @date 2017年11月30日 下午4:27:38
	 * @param naireIds
	 * @return
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult deleteNaire(@RequestParam(value="naireIds[]", required=true)Long[] naireIds) {
		try {
			naireService.delete(naireIds);
			return ResponseResult.ok("删除成功");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.ok("删除失败");
		}
	}
	
	/**
	 * @description: 删除问卷(移至回收站)
	 * @author ylsh
	 * @date 2018年2月27日 下午12:08:23 
	 * @param naireId
	 * @return
	 */
	@RequestMapping(value="/recycle", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult recycle(@RequestParam(value="id", required=true)Long naireId) {
		try {
			naireService.recycle(naireId);
			return ResponseResult.ok();
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.ok("删除失败");
		}
	}
	
	/**
	 * @description: 恢复问卷(回收站中存在的)
	 * @author ylsh
	 * @date 2018年2月27日 下午12:15:20 
	 * @param naireIds
	 * @return
	 */
	@RequestMapping(value="/restore", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult restore(@RequestParam(value="naireIds[]", required=true)Long[] naireIds) {
		try {
			naireService.restore(naireIds);
			return ResponseResult.ok();
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.ok("删除失败");
		}
	}
	
	/**
	 * @description: 获取回收站数据
	 * @author ylsh
	 * @date 2018年2月27日 下午2:39:52 
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getRecycledData", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult getRecycledData(HttpSession session) {
		try {
			TbUser user = (TbUser) session.getAttribute("user");
			List<TbNaire> recycledList = naireService.getRecycledData(user.getId());
			return ResponseResult.ok(recycledList);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.ok(e.getMessage());
		}
	}

	/**
	 * @description:保存问卷
	 * @author ylsh
	 * @date 2017年11月30日 下午4:28:11
	 * @param session
	 * @param naire
	 * @return
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult save(HttpSession session, @RequestBody TbNaire naire) {
		TbUser user = (TbUser) session.getAttribute("user");
		naire.setUserId(user.getId());
		try {
			naireService.save(naire);
			return ResponseResult.ok("保存成功");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}

	/**
	 * @description:预览问卷页面
	 * @author ylsh
	 * @date 2017年11月30日 下午4:29:38
	 * @param model
	 * @param naireId
	 * @return
	 */
	@RequestMapping("/preview/{id}")
	@ResponseBody
	public Object preview(
			HttpSession session, 
			HttpServletRequest request,
			@PathVariable("id")Long naireId,
			@RequestParam(value="pwd", required=false, defaultValue="")String password) {
		ModelAndView modelAndView = new ModelAndView();
		TbNaire naire = naireService.get(naireId);
		{
			// 判断数据存不存在
			if (naire == null || naire.getId() == null) {
				modelAndView.setViewName("error/404");
				return modelAndView;
			}
		}
		{
			// 若当前问卷还未发布，只有问卷的编辑者才可访问(已发布的问卷和已结束（不准回答）的问卷仍可访问)
			if (naire.getStatus() == 1) {
				TbUser user = (TbUser) session.getAttribute("user");
				if (user == null || !user.getId().equals(naire.getUserId())) {
					modelAndView.setViewName("error/404");
					return modelAndView;
				}
			}
		}
		{
			// 回收站内的问卷都不允许访问，拥有者也只能先恢复在访问
			if (naire.getDelFlag() == 1) {
				modelAndView.setViewName("error/404");
				return modelAndView;
			}
		}
		{
			// 验证密码（若有）
			if (StringUtils.isNotBlank(naire.getPassword())) {
				String token = "token" + naire.getId();
				Object tokenObj = session.getAttribute("token");
				if (token.equals(tokenObj)) {
					// 如果有令牌，允许访问，并清除此次令牌
					session.removeAttribute("token");
				} else {
					// 无令牌，则验证密码，密码通过，则并赋予令牌
					if (naire.getPassword().equals(password)) {
						session.setAttribute("token", token);
						return ResponseResult.ok("密码正确");
					} else {
						if (RequestUtil.isAjaxRequest(request)) {
							return ResponseResult.error("密码错误");
						} else {
							modelAndView.addObject("naireTitle", naire.getTitle());
							modelAndView.setViewName("auth");
							return modelAndView;
						}
					}
				}
			}
		}
		{
			// 返回数据
			modelAndView.addObject("isSubmit", 1);
			modelAndView.addObject("naire", naire);
			modelAndView.setViewName("preview");
			session.setAttribute("startTime", new Date());
		}
		return modelAndView;
	}

	/**
	 * @description:预览问卷页面
	 * @author ylsh
	 * @date 2017年11月30日 下午4:30:40
	 * @param model
	 * @param previewData
	 * @return
	 */
	@RequestMapping(value="/preview", method=RequestMethod.POST)
	public String preview(Model model, String previewData) {
		ObjectMapper mapper = new ObjectMapper();
		TbNaire naire = null;
		try {
			naire = mapper.readValue(previewData, TbNaire.class);
			model.addAttribute("naire", naire);
			return "preview";
		} catch (Exception e) {
			e.printStackTrace();
			return "error/500";
		}
	}
	
	/**
	 * @description:问卷库页面
	 * @author ylsh
	 * @date 2017年11月30日 下午4:32:14
	 * @param model
	 * @return
	 */
	@RequestMapping("/lib")
	public String showLibPage(Model model, HttpServletRequest request) {
		// 获取查询参数，只查共用户享的和已发布的
		Map<String, Object> paramMap = RequestUtil.getRequestParam(request);
		paramMap.put("share", 1);
		paramMap.put("nq_status", 1);
		if (paramMap.get("parentCategory") == null) {
			paramMap.put("parentCategory", 1);
		}
		
		// 根据条件查询问卷库
		PageInfo<TbNaire> pageInfo = naireService.list(paramMap);
		model.addAttribute("pageInfo", pageInfo);
		
		// 查询推荐模板(只查前6个，按引用次数排序)
		// 复制查询参数
		Map<String, Object> templatParamMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			templatParamMap.put(entry.getKey(), entry.getValue());
		}
		templatParamMap.put("citations", true);
		templatParamMap.put("pageNum", 1);
		templatParamMap.put("pageSize", 6);
		List<TbNaire> recommendedList =  naireService.list(templatParamMap).getList();
		model.addAttribute("recommendedList", recommendedList);
				
		// 查询问卷分类信息
		Map<String, TbNaireCategory> categoryMap = naireCategoryService.getAllAsMap();
		model.addAttribute("naireCategoryMap", categoryMap);
		
		// 参数回显
		model.addAttribute("queryParameter", paramMap);
		
		return "lib";
	}
	
	
	
	/**
	 * @description:下载问卷
	 * @author ylsh
	 * @date 2017年11月30日 下午4:31:46
	 * @param naireId
	 * @param response
	 */
	@RequestMapping(value="/download/{id}")
	public void downloadNaire(@PathVariable("id")Long naireId, HttpServletResponse response) {
		naireService.downloadNaire(response, naireId);
	}
	
	/**
	 * @description:问卷数据统计界面
	 * @author ylsh
	 * @date 2017年12月5日 下午8:25:55
	 * @return
	 */
	@RequestMapping(value="/statistics/{nid}")
	public String showDataStatisticsPage(
			HttpSession session, 
			Model model, 
			@PathVariable("nid") Long naireId) {
		try {
			// 判断问卷所属
			TbUser user = (TbUser) session.getAttribute("user");
			if (!naireService.isNaireBelongToUser(user.getId(), naireId)) {
				return "error/404";
			}
			TbNaire naire = naireService.getStatisticsData(naireId);
			model.addAttribute("naire", naire);
		} catch(Exception e) {
			e.printStackTrace();
			return "error/500";
		}
		return "dataStatistics";
	}
	
	
	
	/**
	 * @description:收集数据页面
	 * @author ylsh
	 * @date 2017年12月7日 下午5:20:02
	 * @return
	 */
	@RequestMapping("/collect/{id}")
	public String showCollectPage(Model model, @PathVariable("id")Long naireId) {
		model.addAttribute("naireId", naireId);
		return "collect";
	}
	
	
	/**
	 * @description:复制问卷
	 * @author ylsh
	 * @date 2017年12月28日 上午9:58:02
	 */
	@RequestMapping(value="/copy", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult copyNaire(HttpSession session, @RequestParam(value="naireId", required=true)Long naireId) {
		Long userId = ((TbUser) session.getAttribute("user")).getId();
		try {
			naireService.copyNaire(userId, naireId);
			return ResponseResult.ok("引用成功");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}

	
	
	
	/**
	 * @description: 跳转至新增页面（该页面可以选择分类信息）
	 * @author ylsh
	 * @date 2018年4月8日 下午8:21:36 
	 * @param model
	 * @return
	 */
	@RequestMapping("/new")
	public String newNaire(Model model) {
		List<TbNaireCategory> categoryList = naireCategoryService.getAll();
		model.addAttribute("naireCategoryList", categoryList);
		return "new";
	}
	
	/**
	 * @description: 跳转至导入页面
	 * @author ylsh
	 * @date 2018年4月8日 下午8:21:36 
	 * @param model
	 * @param category
	 * @return
	 */
	@RequestMapping("/import")
	public String importNaire(Model model,
			@RequestParam(value="c", required=true) Byte category) {
		model.addAttribute("category", category);
		return "import";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/test")
	public void test(HttpServletRequest req,HttpServletResponse res) {		 
		res.setContentType("text/plain");
	    String callbackFunName =req.getParameter("callbackparam");//得到js函数名称
	    try {
	        res.getWriter().write(callbackFunName + "([ { name:\"John\"}])"); //返回jsonp数据
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}
	
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("/test2")
	@ResponseBody
	public ResponseResult test2(String key) {
		return ResponseResult.ok(key);
	}
	
	

	


	
	@RequestMapping("/test3")
	@ResponseBody
	public Object test3(String key, String callback) throws JsonProcessingException {
		ResponseResult result = ResponseResult.ok(key);
		// 方法1(需拼接返回结果)
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isNotBlank(callback)) {
			return callback + "(" + mapper.writeValueAsString(result) + ")";
		}
		/*
		// 方法2(和方法1最终的返回结果一样，但是需要spring4.1以上版本)
		if (StringUtils.isNotBlank(callback)) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
	*/
		return result;
	}
	
	
	@RequestMapping("/downloadpic")
	public void downloadPic(HttpServletResponse response, String name) throws Exception {
		File file = new File("E:\\Upload\\survey\\userIcon\\"+name);
		InputStream input = new FileInputStream(file);
		OutputStream output = response.getOutputStream();
		response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len=input.read(buffer)) != -1) {
			output.write(buffer, 0, len);
		}
		input.close();
		output.close();
	}
	
	@RequestMapping("/video")
	public void showVideo(HttpServletResponse response) throws Exception {
		File file = new File("C:\\Users\\ylsh\\Documents\\Tencent Files\\"
				+ "1282170798\\FileRecv\\MobileFile\\vivo.mp4");
		InputStream input = new FileInputStream(file);
		OutputStream output = response.getOutputStream();
		byte[] buffer = new byte[1024 * 1024 * 4];
		int len = -1;
		while((len=input.read(buffer)) != -1) {
			output.write(buffer, 0, len);
		}
		input.close();
		output.close();
	}
	
	

}
