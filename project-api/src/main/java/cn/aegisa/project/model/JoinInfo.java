package cn.aegisa.project.model;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * JoinInfo Entity.
 */
public class JoinInfo implements Serializable{
	
	//列信息
	private Integer id;
	
	private Integer cid;
	
	private Integer aid;
	
	private Integer isLeader;
	
	private LocalDateTime joinDate;
	
	private String joinComment;
	
	private String payMethod;
	
	private Integer prepay;
	
	private Integer discount;
	
	private Integer busSeat;
	
	private Integer tableSeat;
	
	private LocalDateTime lastModifyTime;
	

		
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
		
		
	public void setCid(Integer value) {
		this.cid = value;
	}
	
	public Integer getCid() {
		return this.cid;
	}
		
		
	public void setAid(Integer value) {
		this.aid = value;
	}
	
	public Integer getAid() {
		return this.aid;
	}
		
		
	public void setIsLeader(Integer value) {
		this.isLeader = value;
	}
	
	public Integer getIsLeader() {
		return this.isLeader;
	}
		
		
	public void setJoinDate(LocalDateTime value) {
		this.joinDate = value;
	}
	
	public LocalDateTime getJoinDate() {
		return this.joinDate;
	}
		
		
	public void setJoinComment(String value) {
		this.joinComment = value;
	}
	
	public String getJoinComment() {
		return this.joinComment;
	}
		
		
	public void setPayMethod(String value) {
		this.payMethod = value;
	}
	
	public String getPayMethod() {
		return this.payMethod;
	}
		
		
	public void setPrepay(Integer value) {
		this.prepay = value;
	}
	
	public Integer getPrepay() {
		return this.prepay;
	}
		
		
	public void setDiscount(Integer value) {
		this.discount = value;
	}
	
	public Integer getDiscount() {
		return this.discount;
	}
		
		
	public void setBusSeat(Integer value) {
		this.busSeat = value;
	}
	
	public Integer getBusSeat() {
		return this.busSeat;
	}
		
		
	public void setTableSeat(Integer value) {
		this.tableSeat = value;
	}
	
	public Integer getTableSeat() {
		return this.tableSeat;
	}
		
		
	public void setLastModifyTime(LocalDateTime value) {
		this.lastModifyTime = value;
	}
	
	public LocalDateTime getLastModifyTime() {
		return this.lastModifyTime;
	}
		
}

