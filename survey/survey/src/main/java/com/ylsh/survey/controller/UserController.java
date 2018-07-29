package com.ylsh.survey.controller;


import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.pojo.TbUser;
import com.ylsh.survey.service.UserService;
import com.ylsh.survey.util.SessionContext;


/**
 * @description:用户管理Controller
 * @author ylsh
 * @date 2017年11月30日 下午4:23:05
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	
	/**
	 * @description:用户登录
	 * @author ylsh
	 * @date 2017年11月30日 下午4:35:02
	 * @param session
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult login(HttpSession session, TbUser user) {
		ResponseResult result = null;
		try {
			result = userService.login(user);
			if (result.getStatus() == ResponseResult.SUCCESS) {
				session.setAttribute("user", user);
				
				// 当用户登陆时，先检测是否有其他用户也登陆了该账号，若登陆了，强制让上一个用户下线
				String key = "user:" + user.getId();
				HttpSession oldSession = SessionContext.get(key);
				if (oldSession != null) {
					Object obj = oldSession.getAttribute("user");
					if (obj == null) {
						// 如果获取不到用户信息，说明该session处于未登录状态（可能过期，也可能是新生的），清除无用session
						// SessionContext只保存登录用户的session
						SessionContext.remove(key);
					} else {
						oldSession.invalidate();
					}
				}
				// 记录本次登陆用户的session
				SessionContext.put(key, session);
			}
		} catch(Exception e) {
			e.printStackTrace();
			result = ResponseResult.error(e.getMessage());
		}
		return result;
	}

	/**
	 * @description:用户退出
	 * @author ylsh
	 * @date 2017年11月30日 下午4:35:18
	 * @param session
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpSession session, Model model) throws IOException {
		session.invalidate();
		return "redirect:/";
	}

	/**
	 * @description:用户注册
	 * @author ylsh
	 * @date 2017年11月30日 下午4:35:36
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/register/{verifyCode}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult register(
			HttpSession session, 
			@PathVariable("verifyCode") String verifyCode, 
			@RequestBody TbUser user) {
		try {
			// 注册前先核对验证码
			String code = (String) session.getAttribute("verifyCode");
			// 验证用户注册时填写的手机号是否和申请验证码时填写的手机号是否一致
			if (!user.getPhone().equals(session.getAttribute("phone"))) {
				return ResponseResult.error("请重新获取验证码！");
			}
			if (verifyCode != null && verifyCode.equals(code)) {
				// 保存用户信息
				userService.insert(user);
				// 保存成功后清除session里的相关缓存
				session.removeAttribute("verifyCode");
				session.removeAttribute("phone");
				return ResponseResult.ok("发送成功！");
			}
			return ResponseResult.error("验证码错误！");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}


	/**
	 * @description:用户上传头像
	 * @author ylsh
	 * @date 2017年12月3日 下午9:55:46
	 * @param session
	 * @param imageBase64Code
	 * @return
	 */
	@RequestMapping(value="/uploadIcon", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult uploadIcon(HttpSession session, 
			@RequestParam(value="image", required=true)String imageBase64Code) {
		TbUser user = (TbUser) session.getAttribute("user");
		try {
			ResponseResult result = userService.uploadIcon(user, imageBase64Code);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error("上传失败！");
		}
	}


	/**
	 * @description:更新用户信息
	 * @author ylsh
	 * @date 2017年12月3日 下午9:58:49
	 * @param user
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult update(HttpSession session, TbUser user) {
		try {
			user.setId(((TbUser) session.getAttribute("user")).getId());
			
			boolean isUpdatePhone = false;
			// 如果用户是更换手机号码
			if (StringUtils.isNotBlank(user.getPhone())) {
				isUpdatePhone = true;
				// 取出存放在pwd域中的验证码
				String verifyCode = user.getPassword();
				user.setPassword(null);
				// 申请验证码时的手机号和最终填写的手机号不一致
				if (!user.getPhone().equals(session.getAttribute("phone"))) {
					return ResponseResult.error("请重新获取验证码！");
				}
				// 将用户输入的验证码与系统产生的验证码进行比对
				if (!verifyCode.equals(session.getAttribute("verifyCode"))) {
					return ResponseResult.error("验证码错误！");
				}
			}

			ResponseResult result = userService.update(user);
			// 更新成功后，也更新session里缓存的user信息
			if (result.getStatus() == ResponseResult.SUCCESS) {
				// 在更新成功后，user里的信息是最新的用户信息
				session.setAttribute("user", user);
				// 更新手机号码成功后，清除session中的缓存
				if (isUpdatePhone) {
					session.removeAttribute("verifyCode");
					session.removeAttribute("phone");
				}
			}
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}

	/**
	 * @description:用户设置界面
	 * @author ylsh
	 * @date 2017年12月29日 上午11:41:14
	 * @return
	 */
	@RequestMapping("/settings")
	public String settings() {
		return "userSettings";
	}


	/**
	 * @description:发送手机号注册验证码
	 * @author ylsh
	 * @date 2017年12月29日 下午1:12:48
	 * @param session
	 * @param phone
	 * @param checkRepeat
	 * @return
	 */
	@RequestMapping(value="/sendcode", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult sendVerifyCode(
			HttpSession session, 
			@RequestParam(value="phone", required=true)String phone,
			@RequestParam(value="checkRepeat", required=false, defaultValue="true")Boolean checkRepeat) {
		try {
			String verifyCode = userService.sendVerifyCode(phone, checkRepeat);
		//	String verifyCode = "123456";
			session.setAttribute("verifyCode", verifyCode);
			// 保存用户的电话号码，需要在注册时再做一次验证，判断是否是同一个电话号码
			session.setAttribute("phone", phone);
			return ResponseResult.ok("发送成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}
	
	/**
	 * @description: 用户修改密码
	 * @author ylsh
	 * @date 2018年5月23日 上午10:08:56 
	 * @param session
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/resetpwd/{verifyCode}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult resetpwd(HttpSession session, 
			@PathVariable("verifyCode") String verifyCode, 
			@RequestParam("password") String password) {
		try {
			if (!session.getAttribute("verifyCode").equals(verifyCode)) {
				return ResponseResult.error("验证码错误！");
			}
			String phone = (String) session.getAttribute("phone");
			if (StringUtils.isBlank(password) || StringUtils.isBlank(phone)) {
				throw new Exception("非法操作！");
			}
			
			TbUser user = userService.getUser(phone);
			if (user == null) {
				return ResponseResult.error("手机号码不存在！");
			}
			
			// 更新数据
			TbUser userObj = new TbUser();
			userObj.setId(user.getId());
			userObj.setPassword(password);
			userService.update(userObj);
			
			// 清除session缓存
			session.removeAttribute("verifyCode");
			session.removeAttribute("phone");
						
			return ResponseResult.ok("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e.getMessage());
		}
	}

}