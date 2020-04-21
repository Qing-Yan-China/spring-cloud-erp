package com.slq.controller.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.slq.common.Sort;
import com.slq.pojo.TreeMenu;
import com.slq.service.ICommonService;
import com.slq.util.DefaultResponseData;
import com.slq.util.ResponseData;
import com.slq.util.VerificationCodeCreateUtil;

@RestController
@RequestMapping("/api/common")
public class CommonController {
	@Autowired
	private ICommonService commonService;
	@RequestMapping("/descSort")
	public ResponseData getDescSort(Sort sort) {
		System.out.println(sort);
		sort = commonService.getDescSort(sort);
		return DefaultResponseData.successResponseData(sort.getmaxSortNum());
	}
	
	@RequestMapping("/getTreeMenu")
	public ResponseData getTreeMenu(Integer type) {
		List<TreeMenu> treeMenu = commonService.getTreeMenu(type);
		return DefaultResponseData.successResponseData(treeMenu);
	}
	//获取左侧导航菜单数据
	@RequestMapping("/getTreeMenus")
	public ResponseData getTreeMenus() {
		List<TreeMenu> treeMenu = commonService.getTreeMenus();
		System.out.println(JSON.toJSONString(treeMenu));
		return DefaultResponseData.successResponseData(treeMenu);
	}
}
