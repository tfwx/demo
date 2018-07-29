package com.ylsh.survey.dto;

import java.io.Serializable;


/**
 * @description:服务器响应结果
 * @author ylsh
 * @date 2017年11月30日 下午4:38:34
 */
public class ResponseResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final Integer SUCCESS = 200;
	public static final Integer ERROR = -1;
	
	/**
	 * 响应业务状态
	 */
	private Integer status;
	
	/**
	 * 响应消息
	 */
	private Object msg;
	
	public ResponseResult() {}
	
	public ResponseResult(Integer status, Object msg) {
		this.status = status;
		this.msg = msg;
	}


	public static ResponseResult ok() {
		return new ResponseResult(SUCCESS, null);
	}
	
	public static ResponseResult ok(Object msg) {
		return new ResponseResult(SUCCESS, msg);
	}
	
	public static ResponseResult error(Object errMsg) {
		return new ResponseResult(ERROR, errMsg);
	}
	
	public static ResponseResult error() {
		return new ResponseResult(ERROR, "服务器异常");
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}
	

}
