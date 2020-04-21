package com.slq.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.slq.common.Params;
import com.slq.overwrite.MyPageInfo;
import com.slq.pojo.User;
import com.slq.service.IUserService;
import com.slq.util.DefaultResponseData;
import com.slq.util.ResponseData;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@GetMapping("/checkuid")
	public ResponseData checkuid(String uid) {
		boolean fals = userService.checkuid(uid);
		if(fals) {
			return DefaultResponseData.successResponseData(true);
		}
		return DefaultResponseData.errorResponseData(null);
	}
	
	@PostMapping("/updateActiveUser")
	public ResponseData updateActiveUser(User user) {
		System.out.println("user:"+user);
		try {
			userService.updateActiveUser(user);
			return DefaultResponseData.successResponseData(true);
		} catch (Exception e) {
			return DefaultResponseData.errorResponseData(e.getMessage());
		}
	}
	@PostMapping("/equalsPwd")
	public ResponseData equalsPwd(String upwd) {
		System.out.println("upwd:"+upwd);
		try {
			userService.equalsPwd(upwd);
			return DefaultResponseData.successResponseData(true);
		} catch (Exception e) {
			return DefaultResponseData.errorResponseData(e.getMessage());
		}
	}
	@PostMapping("/updateUpwd")
	public ResponseData updatePwd(String newUpwd) {
		try {
			userService.updatePwd(newUpwd);
			return DefaultResponseData.successResponseData(null);
		} catch (Exception e) {
			return DefaultResponseData.errorResponseData(e.getMessage());
		}
	}
	@PostMapping("/search")
	public ResponseData search(@RequestBody Params params) {
		System.out.println(params);
		PageInfo<User> userPageInfo = userService.getUserPageInfo(params);
		return DefaultResponseData.successResponseData(userPageInfo);
	}
	@GetMapping("/getUserOwnRole/{id}")
	public ResponseData getUserOwnRole(@PathVariable("id")Integer id) {
		List<Integer> rids = userService.getUserOwnRole(id);
		return DefaultResponseData.successResponseData(rids);
	}
	/***
	 * 设置用户的角色
	 * @param params
	 * @return
	 */
	@PostMapping("/setUserOwnRole")
	public ResponseData setUserOwnRole(@RequestBody JSONObject params) {
		Integer id=params.getInteger("id");
		List<Integer> newRids=params.getObject("newRids", new ArrayList<Integer>().getClass());
		List<Integer> oldRids=params.getObject("oldRids", new ArrayList<Integer>().getClass());
		try {
			int i = userService.setUserOwnRole(id,newRids,oldRids).size();
			return DefaultResponseData.successResponseData(i);
		} catch (Exception e) {
			// TODO: handle exception
			return DefaultResponseData.errorResponseData("保存失败!");
		}
	}
	@PostMapping("/insert")
	public ResponseData insertUser(@RequestBody User user) {
		try {
			userService.insertUser(user);
			return DefaultResponseData.successResponseData(user.getUid());
		} catch (Exception e) {
			return DefaultResponseData.errorResponseData(e.getMessage());
		}
	}
	
	@GetMapping("/byId/{uid}")
	public ResponseData byId(@PathVariable("uid")String uid) {
		User user = userService.getUserByuId(uid);
		return DefaultResponseData.successResponseData(user);
	}
}
