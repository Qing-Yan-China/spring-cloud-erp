package com.slq.pojo.common;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

@Data
@TableName("unit_key")
public class Unit implements Serializable{

	//主键注解  value 为主键名,idtype.auto 数据库id自增
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	
	
	private String table_column;
	
	private String message;
	@TableField(exist = false)//是否为数据库中的字段  默认为true 
	private List<String> units;
	
}
