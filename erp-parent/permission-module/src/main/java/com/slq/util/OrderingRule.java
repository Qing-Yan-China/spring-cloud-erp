package com.slq.util;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestParam;

import com.slq.constant.Constant;

import lombok.Data;

@Data
public class OrderingRule implements Serializable{

	private String prop;
	
	private String order=Constant.ORDER_ASC;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		if(order.contains(Constant.ORDER_DESC)) {
			this.order = Constant.ORDER_DESC;
		}
	}

	
	
	
}
