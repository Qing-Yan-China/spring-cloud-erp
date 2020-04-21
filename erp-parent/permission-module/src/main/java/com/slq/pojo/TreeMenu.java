package com.slq.pojo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@Data
public class TreeMenu implements Serializable{
	private Integer id;
	
	private String title;
	
	private List<TreeMenu> children;
	
	private Integer type;//类型  1资源权限 2菜单权限
	
	private String icon;
	
	private String url;
	
	private String componentName;
	
	private boolean showMenu;
}
