package com.ylsh.survey.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @description: 问卷分类信息实体
 * @author ylsh
 * @date 2018年1月12日 上午9:31:38
 */
@JsonIgnoreProperties(value = {"handler"})
public class TbNaireCategory implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Byte id;					// 主键ID

    private String categoryDesc;		// 分类描述

    private String categoryBrief;		// 分类概要

    private Byte parentId;				// 父结点ID
    
    private TbNaireCategory parentNode; // 父节点信息

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

    public String getCategoryBrief() {
        return categoryBrief;
    }

    public void setCategoryBrief(String categoryBrief) {
        this.categoryBrief = categoryBrief == null ? null : categoryBrief.trim();
    }

    public Byte getParentId() {
        return parentId;
    }

    public void setParentId(Byte parentId) {
        this.parentId = parentId;
    }

	public TbNaireCategory getParentNode() {
		return parentNode;
	}

	public void setParentNode(TbNaireCategory parentNode) {
		this.parentNode = parentNode;
	}

	
}