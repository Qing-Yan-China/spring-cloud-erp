package com.slq.common;

import java.io.Serializable;

import lombok.Data;


public class Sort implements Serializable{

	private static final Integer DEFAULT_SORT_NUM=1; 
	private String tableName;
	
	private String columnName;
	
	private Integer maxSortNum;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Integer getmaxSortNum() {
		return maxSortNum;
	}

	public void setmaxSortNum(Integer maxSortNum) {
		if(maxSortNum==null) {
			this.maxSortNum=DEFAULT_SORT_NUM;
		}else {
			this.maxSortNum = ++maxSortNum;
		}
	}

	@Override
	public String toString() {
		return "sortNum [tableName=" + tableName + ", columnName=" + columnName + ", maxSortNum=" + maxSortNum + "]";
	}
	
	
	
}
