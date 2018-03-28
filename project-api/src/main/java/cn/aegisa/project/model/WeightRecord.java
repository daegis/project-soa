package cn.aegisa.project.model;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * WeightRecord Entity.
 */
public class WeightRecord implements Serializable{
	
	//列信息
	private Integer id;
	
	private String name;
	
	private Double weight;
	
	private LocalDateTime datetime;
	

		
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
		
		
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
		
		
	public void setWeight(Double value) {
		this.weight = value;
	}
	
	public Double getWeight() {
		return this.weight;
	}
		
		
	public void setDatetime(LocalDateTime value) {
		this.datetime = value;
	}
	
	public LocalDateTime getDatetime() {
		return this.datetime;
	}
		
}

