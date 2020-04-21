package com.slq.common;

import java.util.List;

import lombok.Data;
@Data
public class CascadeChoice {

	private String title;//显示的名称
	
	private Integer value;//真正的值数据
	
	private Integer type;
	
	private List<CascadeChoice> children;
}
