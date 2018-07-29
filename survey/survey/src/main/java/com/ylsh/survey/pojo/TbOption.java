package com.ylsh.survey.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @description: 选项信息实体
 * @author ylsh
 * @date 2018年1月12日 上午9:26:53
 */
@JsonIgnoreProperties(value = {"handler"})
public class TbOption implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;					// 主键ID

    private String optionDesc;			// 选项描述

    private Long questionId;			// 所属问题ID

    private Long selectCount;			// 被选择的数量
    
    private String selectPercent;		// 选项所占百分比
    
    private Long optionIndex;			// 该选项在整题目中的序号(从0开始)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionDesc() {
        return optionDesc;
    }

    public void setOptionDesc(String optionDesc) {
        this.optionDesc = optionDesc == null ? null : optionDesc.trim();
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(Long selectCount) {
        this.selectCount = selectCount;
    }

	public String getSelectPercent() {
		return selectPercent;
	}

	public void setSelectPercent(String selectPercent) {
		this.selectPercent = selectPercent;
	}

	public Long getOptionIndex() {
		return optionIndex;
	}

	public void setOptionIndex(Long optionIndex) {
		this.optionIndex = optionIndex;
	}

}