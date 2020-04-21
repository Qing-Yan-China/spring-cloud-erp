package com.slq.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {

	private Integer statusCord;//200成功  其他的都市失败
	
	private String message;//信息  比如说响应成功 新增成功 新增失败
	
	private boolean gotoPage;
	
	private String url;
	
	private Object data;//

	
	public ResponseData(Integer statusCord, String message, Object data) {
		super();
		this.statusCord = statusCord;
		this.message = message;
		this.data = data;
	}

	public ResponseData(String message, Object data) {
		super();
		this.message = message;
		this.data = data;
	}

	public ResponseData(Integer statusCord, String message, boolean gotoPage, String url) {
		super();
		this.statusCord = statusCord;
		this.message = message;
		this.gotoPage = gotoPage;
		this.url = url;
	}

	public ResponseData(Integer statusCord, String message) {
		super();
		this.statusCord = statusCord;
		this.message = message;
	}
	
}
