package com.ylsh.survey.pojo;

import java.util.Date;

public class TbRespondentInfo {
    private Long id;

    private Date startTime;				// 答题开始时间

    private Date endTime;				// 答题结束时间

    private String duration;			// 答题时长

    private String browser;				// 答题者浏览器信息

    private String system;				// 答题者操作系统

    private String ip;					// 答题者IP地址
    
    private Integer provinceId;			// 答题者所在省（市）ID

    private Long naireId;				// 答题者所回答的问卷ID
    
    private TbNaire naire;				// 答卷信息
    
    private String address;				// 区域地址
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
    	// 丢弃毫秒，否则，mysql会把毫秒数4舍5入，导致可能误差1秒
    	startTime.setTime(startTime.getTime()/1000*1000);
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
    	// 丢弃毫秒，否则，mysql会把毫秒数4舍5入
    	endTime.setTime(endTime.getTime()/1000*1000);
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser == null ? null : browser.trim();
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system == null ? null : system.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
    
    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Long getNaireId() {
        return naireId;
    }

    public void setNaireId(Long naireId) {
        this.naireId = naireId;
    }

	public TbNaire getNaire() {
		return naire;
	}

	public void setNaire(TbNaire naire) {
		this.naire = naire;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
    
    
}