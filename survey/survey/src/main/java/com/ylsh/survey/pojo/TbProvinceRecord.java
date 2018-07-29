package com.ylsh.survey.pojo;

public class TbProvinceRecord {
    private Long id;

    private Long naireId;

    private Integer provinceId;

    private Long ansId;
    
    public TbProvinceRecord() {}
    
    public TbProvinceRecord(Long naireId, Integer provinceId, Long ansId) {
    	this.naireId = naireId;
    	this.provinceId = provinceId;
    	this.ansId = ansId;    	
    }

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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Long getAnsId() {
        return ansId;
    }

    public void setAnsId(Long ansId) {
        this.ansId = ansId;
    }
}