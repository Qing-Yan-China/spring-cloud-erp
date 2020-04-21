package com.slq.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Role implements Serializable{

	private static final long serialVersionUID = 1749257499245521490L;
	
	private Integer rid;
	
	private String rname;
	
	private String initial;
	
	private boolean fixed;
	
	private List<Permission> permission;
	
	private Integer roleStatus;
	
	private Integer sort;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date updateTime;
	
	private String describe;

	
	
}
