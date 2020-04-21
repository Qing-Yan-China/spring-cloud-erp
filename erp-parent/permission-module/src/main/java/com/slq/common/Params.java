package com.slq.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.slq.util.OrderingRule;

import lombok.Data;

@Data
public class Params implements Serializable{

	//private static final long serialVersionUID = 1L;
	
	private Integer pageNum;
	
	private Integer pageSize;
	
	private String keyWord;

	private OrderingRule orderingRule;//排序规则
	
	//补充的参数
	private Map<String, String> replenishParams;

	public Map<String, String> getReplenishParams() {
		return replenishParams;
	}

	public void setReplenishParams(String key,String value) {
		if(this.replenishParams==null) {
			this.replenishParams=new HashMap<String, String>();
		}
		this.replenishParams.put(key, value);
	}
	
	
	
}
