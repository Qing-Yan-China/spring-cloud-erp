package com.slq.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class User implements Serializable{

	private Integer id;
	private String uid;
	private String uname;
	private String initial;
	private String upwd;
	private Integer userStatus;//1可用 2冻结
	private String imgPath;
	private String address;
	private Integer sex;
	private String signature;//个性签名
	private Integer isadmin;
	private Integer deptId;
	private Integer mgr;
	private Integer isFixed;
	private Integer sort;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GM+8")
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GM+8")
	private Date lastDate;
	
	private List<String> roles;
	
	private List<String> permissions;

	
	
}
