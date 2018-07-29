package com.ylsh.survey.pojo;

import java.io.Serializable;

/**
 * @description: 用户信息实体
 * @author ylsh
 * @date 2018年1月9日 上午11:30:37
 */
public class TbUser implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;					// 主键ID
    
    private String phone;				// 电话号码（用户账户）

    private String password;			// 账户密码

    private String nick;				// 用户昵称

    private String icon;				// 用户头像
    
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}