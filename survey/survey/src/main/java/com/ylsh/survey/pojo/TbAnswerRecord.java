package com.ylsh.survey.pojo;

import java.util.List;

public class TbAnswerRecord {
    private Long id;

    private Long naireId;

    private Long questionId;

    private Long answerId;

    private Long respondentId;
    
    private List<TbQuestion> questionList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNaireId() {
        return naireId;
    }

    public void setNaireId(Long naireId) {
        this.naireId = naireId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getRespondentId() {
        return respondentId;
    }

    public void setRespondentId(Long respondentId) {
        this.respondentId = respondentId;
    }
    
    public List<TbQuestion> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<TbQuestion> questionList) {
		this.questionList = questionList;
	}
}