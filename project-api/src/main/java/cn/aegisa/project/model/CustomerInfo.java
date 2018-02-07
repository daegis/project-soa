package cn.aegisa.project.model;

import java.io.Serializable;


/**
 * CustomerInfo Entity.
 */
public class CustomerInfo implements Serializable{
	
	//列信息
	private Integer id;
	
	private String nickname;
	
	private String realName;
	
	private String idNumber;
	
	private String telephone;
	
	private java.util.Date lastModifyTime;
	
	private String comment;
	
	private String address;
	

		
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
		
		
	public void setNickname(String value) {
		this.nickname = value;
	}
	
	public String getNickname() {
		return this.nickname;
	}
		
		
	public void setRealName(String value) {
		this.realName = value;
	}
	
	public String getRealName() {
		return this.realName;
	}
		
		
	public void setIdNumber(String value) {
		this.idNumber = value;
	}
	
	public String getIdNumber() {
		return this.idNumber;
	}
		
		
	public void setTelephone(String value) {
		this.telephone = value;
	}
	
	public String getTelephone() {
		return this.telephone;
	}
		
		
	public void setLastModifyTime(java.util.Date value) {
		this.lastModifyTime = value;
	}
	
	public java.util.Date getLastModifyTime() {
		return this.lastModifyTime;
	}
		
		
	public void setComment(String value) {
		this.comment = value;
	}
	
	public String getComment() {
		return this.comment;
	}
		
		
	public void setAddress(String value) {
		this.address = value;
	}
	
	public String getAddress() {
		return this.address;
	}
		
}

