package com.slq.overwrite;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.github.pagehelper.PageInfo;

import lombok.Data;


@Data
public class MyPageInfo<T> extends PageInfo<T> {

	
	private static int defaultPageNum=1;

	private static int defaultPageSize=5;
	
	private String keyWord;

	private void init() {
		if(this.getPageNum()==0) {
			this.setPageNum(MyPageInfo.defaultPageNum);
		}
		if(this.getPageSize()==0) {
			this.setPageSize(MyPageInfo.defaultPageSize);
		}
	}
	public MyPageInfo() {
		super();
		init();
		// TODO Auto-generated constructor stub
	}

	public MyPageInfo(List<T> list, int navigatePages) {
		super(list, navigatePages);
		init();
		// TODO Auto-generated constructor stub
	}

	public MyPageInfo(List<T> list) {
		super(list);
		init();
		// TODO Auto-generated constructor stub
	}
	
	

}
