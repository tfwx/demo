package com.ylsh.survey.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @description: 问卷信息实体
 * @author ylsh
 * @date 2018年1月12日 上午9:21:45
 */
@JsonIgnoreProperties(value = {"handler"})
public class TbNaire implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Long id;				// 主键ID

    private String title;			// 问卷标题

    private String naireDesc;		// 问卷描述

    private Date createTime;		// 创建时间

    private Date endTime;			// 最后一次修改时间

    private Long collectCount;		// 收集份数

    private Long userId;			// 所属用户ID

    private Byte categoryId;		// 所属分类ID

    private Byte status;			// 当前状态
    
    private Long citationsCount;	// 引用次数
    
    private Byte delFlag;			// 删除标记
    
    private Boolean share;			// 是否共享

    private Byte subject;			// 主题样式
    
    private Byte phoneStyle;		// 手机主题
    
    private String password;
    
    private TbUser user;			// 所属用户
    
    private List<TbQuestion> questionList = new ArrayList<TbQuestion>();	// 包含的问题
    
    private TbNaireCategory category = new TbNaireCategory();				// 所属分类


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getNaireDesc() {
        return naireDesc;
    }

    public void setNaireDesc(String naireDesc) {
        this.naireDesc = naireDesc == null ? null : naireDesc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Long collectCount) {
        this.collectCount = collectCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Byte categoryId) {
        this.categoryId = categoryId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

	public List<TbQuestion> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<TbQuestion> questionList) {
		this.questionList = questionList;
	}

	public TbNaireCategory getCategory() {
		return category;
	}

	public void setCategory(TbNaireCategory category) {
		this.category = category;
	}

	public TbUser getUser() {
		return user;
	}

	public void setUser(TbUser user) {
		this.user = user;
	}
	
	public Long getCitationsCount() {
        return citationsCount;
    }

    public void setCitationsCount(Long citationsCount) {
        this.citationsCount = citationsCount;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }
    
    public Boolean getShare() {
        return share;
    }

    public void setShare(Boolean share) {
        this.share = share;
    }

    public Byte getSubject() {
        return subject;
    }

    public void setSubject(Byte subject) {
        this.subject = subject;
    }

	public Byte getPhoneStyle() {
		return phoneStyle;
	}

	public void setPhoneStyle(Byte phoneStyle) {
		this.phoneStyle = phoneStyle;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}