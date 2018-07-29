package com.ylsh.survey.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"handler"})
public class TbQuestionCategory implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Byte id;						// 主键ID

    private String categoryDesc;			// 分类描述

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc == null ? null : categoryDesc.trim();
    }
}