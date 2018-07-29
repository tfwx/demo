package com.ylsh.survey.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @description: 问题信息实体
 * @author ylsh
 * @date 2018年1月12日 上午9:22:14
 */
@JsonIgnoreProperties(value = {"handler"})
public class TbQuestion implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Long id;					// 主键ID

    private String questionDesc;		// 问题描述

    private Long naireId;				// 所属问卷ID

    private Byte categoryId;			// 所属分类ID
    
    private Integer orderNumber;				// 题目顺序
    
    private TbQuestionCategory category = new TbQuestionCategory();		// 所属分类
    
    private List<TbOption> optionList = new ArrayList<TbOption>();		// 包含的选项

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc == null ? null : questionDesc.trim();
    }

    public Long getNaireId() {
        return naireId;
    }

    public void setNaireId(Long naireId) {
        this.naireId = naireId;
    }

    public Byte getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Byte categoryId) {
        this.categoryId = categoryId;
    }

	public List<TbOption> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<TbOption> optionList) {
		this.optionList = optionList;
	}

	public TbQuestionCategory getCategory() {
		return category;
	}

	public void setCategory(TbQuestionCategory category) {
		this.category = category;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

}