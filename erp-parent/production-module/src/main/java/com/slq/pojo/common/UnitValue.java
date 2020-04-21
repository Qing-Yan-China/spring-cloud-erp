package com.slq.pojo.common;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value="unit_value")
public class UnitValue {

	private Integer parent_id;
	
	private String unit;
	
}
