package cn.aegisa.project.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * CustomerInfo Entity.
 */
@Data
public class CustomerInfo implements Serializable{
	
	//列信息
	private Integer id;
	
	private String nickname;
	
	private String realName;
	
	private String idNumber;
	
	private String telephone;
	
	private LocalDateTime lastModifyTime;
	
	private String comment;
	
	private String address;


}

