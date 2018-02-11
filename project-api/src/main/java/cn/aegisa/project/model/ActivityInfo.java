package cn.aegisa.project.model;

import java.io.Serializable;


/**
 * ActivityInfo Entity.
 */
public class ActivityInfo implements Serializable{
	
	//列信息
	private Integer id;
	
	private String activityName;
	
	private java.util.Date activityDate;
	
	private Integer dayCount;
	
	private Integer price;
	
	private java.util.Date createTime;
	

		
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
		
		
	public void setActivityName(String value) {
		this.activityName = value;
	}
	
	public String getActivityName() {
		return this.activityName;
	}
		
		
	public void setActivityDate(java.util.Date value) {
		this.activityDate = value;
	}
	
	public java.util.Date getActivityDate() {
		return this.activityDate;
	}
		
		
	public void setDayCount(Integer value) {
		this.dayCount = value;
	}
	
	public Integer getDayCount() {
		return this.dayCount;
	}
		
		
	public void setPrice(Integer value) {
		this.price = value;
	}
	
	public Integer getPrice() {
		return this.price;
	}
		
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
}

