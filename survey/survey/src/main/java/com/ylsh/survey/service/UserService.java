package com.ylsh.survey.service;

import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.pojo.TbUser;


/**
 * @description:用户Service接口
 * @author ylsh
 * @date 2017年11月30日 下午4:54:33
 */
public interface UserService {
	
	ResponseResult login(TbUser user);
	
	void insert(TbUser user);
	
	ResponseResult update(TbUser user);
	
	ResponseResult uploadIcon(TbUser user, String imageBase64Code);

	String sendVerifyCode(String phone, Boolean checkRepeat);
	
	int countByPhone(String phone);
	
	TbUser getUser(String phone);

}
