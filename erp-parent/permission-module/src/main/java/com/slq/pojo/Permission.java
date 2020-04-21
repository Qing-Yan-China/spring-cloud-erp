package com.slq.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Permission implements Serializable{

	//private static final long serialVersionUID = 1L;
	
	private Integer pId;
	private Integer parentId;
	private Permission parentPermission;
	private String pName;
	private String initial;
	private Integer pType;
	private String icon;
	private String resource;
	private String url;
	private Integer permissionStatus;
	private Integer sort;
	private String describe;
	private Integer isFixed;
	
	private String componentName;//组件名称
	
	private boolean showMenu;//是否显示到菜单栏
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public Integer getpType() {
		return pType;
	}
	public void setpType(Integer pType) {
		this.pType = pType;
	}
	
	
	
}
