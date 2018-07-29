package com.ylsh.survey.service.impl;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.mapper.TbUserMapper;
import com.ylsh.survey.pojo.TbUser;
import com.ylsh.survey.pojo.TbUserExample;
import com.ylsh.survey.service.UserService;
import com.ylsh.survey.util.Base64Util;
import com.ylsh.survey.util.MD5Util;
import com.ylsh.survey.util.SMSUtil;


/**
 * @description:用户管理Service
 * @author ylsh
 * @date 2017年11月30日 下午4:57:34
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;

	@Value("${upload_path}")
	private String UPLOAD_PATH;				// 图片上传路径

	@Value("${virtual_access_path}")
	private String VIRTUAL_ACCESS_PATH;		// 图片虚拟访问路径


	/**
	 * @description:用户登陆验证
	 * @author ylsh
	 * @date 2017年11月30日 下午4:58:01
	 * @param user
	 * @return
	 * @see com.ylsh.survey.service.UserService#login(com.ylsh.survey.pojo.TbUser)
	 */
	@Transactional(readOnly=true)
	@Override
	public ResponseResult login(TbUser user) {
		// 设置查询条件，根据phone和password查询用户
		String password = MD5Util.toMD5(user.getPassword());
		TbUserExample example = new TbUserExample();
		TbUserExample.Criteria criteria = example.createCriteria();
		criteria.andPhoneEqualTo(user.getPhone());
		criteria.andPasswordEqualTo(password);
		List<TbUser> list = userMapper.selectByExample(example);
		if (list != null && list.size() == 1) {
			// 用户存在
			TbUser u = list.get(0);
			u.setPassword(null);		// 不准将密码一起返回
			// 属性拷贝
			BeanUtils.copyProperties(u, user);
			return ResponseResult.ok("登陆成功！");
		} else if (list.isEmpty()) {
			// 用户不存在
			return ResponseResult.error("用户名或密码错误！");
		} else {
			// 数据异常（存在重复数据）,一般不会出现该情况
			return ResponseResult.error("服务器异常！");
		}
	}

	/**
	 * @description:新增用户
	 * @author ylsh
	 * @date 2017年11月30日 下午4:58:18
	 * @param user
	 * @return
	 * @see com.ylsh.survey.service.UserService#insertUser(com.ylsh.survey.pojo.TbUser)
	 */
	@Transactional
	@Override
	public void insert(TbUser user) {
		// 插入用户之前先判断用户填写的电话是否已被注册
		int count = countByPhone(user.getPhone());
		if (count != 0) {
			throw new RuntimeException("该手机号码已被注册！");
		}
		// 对密码进行MD5加密
		String md5Password = MD5Util.toMD5(user.getPassword());
		user.setPassword(md5Password);
		userMapper.insert(user);		
	}

	/**
	 * @description:更新用户信息
	 * @author ylsh
	 * @date 2017年11月30日 下午4:58:41
	 * @param user
	 * @return
	 * @see com.ylsh.survey.service.UserService#updateUserData(com.ylsh.survey.pojo.TbUser)
	 */
	@Transactional
	@Override
	public ResponseResult update(TbUser user) {
		// 先判断此电话号码是否被其它用户绑定
		if (StringUtils.isNotBlank(user.getPhone())) {
			TbUserExample example = new TbUserExample();
			TbUserExample.Criteria criteria = example.createCriteria();
			criteria.andPhoneEqualTo(user.getPhone()).andIdNotEqualTo(user.getId());
			int count = userMapper.countByExample(example);
			if (count > 0) {
				return ResponseResult.error("该手机号码已被注册！");
			}
		}
		
		// 密码加密后存储
		if (user.getPassword() != null) {
			user.setPassword(MD5Util.toMD5(user.getPassword()));
		}

		// 更新用户信息
		userMapper.updateByPrimaryKeySelective(user);
		// 返回最新的用户信息（不准返回密码）
		TbUser u = userMapper.selectByPrimaryKey(user.getId());
		BeanUtils.copyProperties(u, user);
		user.setPassword(null);
		return ResponseResult.ok();
	}

	/**
	 * @description:保存用户上传的头像
	 * @author ylsh
	 * @date 2017年12月1日 下午10:51:57
	 * @param imageBase64Str
	 * @return
	 * @see com.ylsh.survey.service.UserService#uploadIcon(java.lang.String)
	 */
	@Transactional
	@Override
	public ResponseResult uploadIcon(TbUser user, String imageBase64Code) {
		// 分离前缀，解码图片时不需要前缀
		String[] s = imageBase64Code.split(",");
		String imageName = UUID.randomUUID().toString().replaceAll("\\-", "") + ".png";
		String imgFilePath = UPLOAD_PATH + File.separator + imageName;
		boolean success = Base64Util.decodeImage(imgFilePath, s[1]);
		System.err.println(imgFilePath);
		if (success) {
			// 提取旧头像（若有）
			String oldIcon = user.getIcon();

			// 更新头像
			String iconVirtualPath = VIRTUAL_ACCESS_PATH + "/" + imageName;
			TbUser u = new TbUser();
			u.setId(user.getId());
			u.setIcon(iconVirtualPath);
			ResponseResult result = update(u);
			// 如果更新成功
			if (result.getStatus() == ResponseResult.SUCCESS) {
				// 更新session里用户的头像并返回虚拟访问地址
				user.setIcon(iconVirtualPath);
				result.setMsg(iconVirtualPath);
				// 如果该用户之前已经上传过头像
				if (!StringUtils.isBlank(oldIcon)) {
					imageName = oldIcon.replace(VIRTUAL_ACCESS_PATH + "/", "");
					// 删除原来的头像文件
					File oldIconFile = new File(UPLOAD_PATH + File.separator + imageName);
					if (oldIconFile.exists()) {
						boolean isDelete = oldIconFile.delete();
						if (!isDelete) {
							System.err.println("删除用户旧头像失败，用户ID：" + user.getId());
						}
					}
				}
			}
			return result;
		}

		return ResponseResult.error("上传失败！");
	}


	/**
	 * @description:向注册用户发送验证码
	 * @author ylsh
	 * @date 2017年12月29日 下午1:17:12
	 * @param phone
	 * @param checkRepeat 当为true时，则验证手机号码是否存在数据库，若存在则抛出异常
	 * @return
	 * @see com.ylsh.survey.service.UserService#sendVerifyCode(java.lang.String, java.lang.Boolean)
	 */
	@Transactional(readOnly = true)
	@Override
	public String sendVerifyCode(String phone, Boolean checkRepeat) {
		if (checkRepeat) {
			// 先判断该手机号码是否已被注册
			int count = countByPhone(phone);
			if (count > 0) {
				throw new RuntimeException("该手机号码已被注册！");
			}
		}
		// 随机产生6位验证码
		String verifyCode = (int)(Math.random() * 1000000) + "";
		// 发送短信
		boolean success = SMSUtil.sendSMS(verifyCode, phone);
		if (!success) {
			throw new RuntimeException("验证码发送失败！");
		}
		return verifyCode;
	}

	/**
	 * @description: 根据手机号码统计用户数量
	 * @author ylsh
	 * @date 2018年4月13日 下午1:29:31 
	 * @param phone
	 * @return
	 * @see com.ylsh.survey.service.UserService#countByPhone(java.lang.String)
	 */
	@Transactional(readOnly = true)
	@Override
	public int countByPhone(String phone) {
		TbUserExample example = new TbUserExample();
		TbUserExample.Criteria criteria = example.createCriteria();
		criteria.andPhoneEqualTo(phone);
		return userMapper.countByExample(example);
	}


	@Override
	public TbUser getUser(String phone) {
		TbUserExample example = new TbUserExample();
		TbUserExample.Criteria criteria = example.createCriteria();
		criteria.andPhoneEqualTo(phone);
		List<TbUser> userList = userMapper.selectByExample(example);
		return (userList == null || userList.isEmpty()) ? null : userList.get(0);
	}



}
