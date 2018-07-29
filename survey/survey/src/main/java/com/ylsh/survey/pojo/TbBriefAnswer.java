package com.ylsh.survey.pojo;

/**
 * @description: 简答题信息实体
 * @author ylsh
 * @date 2018年1月12日 上午9:36:17
 */
public class TbBriefAnswer {
    private Long id;					// 主键ID

    private Long questionId;			// 所属问题ID

    private String answerContent;		// 答案内容

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent == null ? null : answerContent.trim();
    }
}