package com.slq.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.slq.common.Params;
import com.slq.pojo.Role;
import com.slq.service.IRoleService;
import com.slq.util.DefaultResponseData;
import com.slq.util.ResponseData;

@RestController
@RequestMapping("/api/role")
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	@GetMapping("/usableChooser")
	public ResponseData getUsableRoleChooser() {
		List<Role> roles = roleService.getUsableRoleChooser();
		return DefaultResponseData.successResponseData(roles);
	}
	@PostMapping("/search")
	public ResponseData getRolePageInfo(@RequestBody Params params){
		PageInfo<Role> rolePage=roleService.getRolePageInfo(params);
		
		return DefaultResponseData.successResponseData(rolePage);
	}
	
	@PostMapping("/insert")
	public ResponseData insertRole(@RequestBody Role role) {
		System.out.println(role);
		Integer rId=roleService.insertRole(role);
		if(rId>0)
			return DefaultResponseData.successResponseData(rId);
		return DefaultResponseData.ADD_ERROR;
	}
	@GetMapping("/delete/{rid}")
	public ResponseData deleteRole(@PathVariable("rid")Integer rId) {
		try {
			int i=roleService.deleteRole(rId);
			return DefaultResponseData.successResponseData(i);
		} catch (Exception e) {
			return DefaultResponseData.DELETE_ERROR;
		}
	}
	@GetMapping("/setRoleState/{rid}/{newStatus}")
	public ResponseData setRoleState(@PathVariable("rid")Integer rid,@PathVariable("newStatus")Integer newStatus) {
		try {
			int i=roleService.setRoleState(rid,newStatus);
			return DefaultResponseData.successResponseData(i);
		} catch (Exception e) {
			return DefaultResponseData.errorResponseData("设置状态失败!");
		}
	}
	@GetMapping("/getById/{rid}")
	public ResponseData getRoleById(@PathVariable("rid")Integer rid) {
		
		return DefaultResponseData.successResponseData(roleService.getRoleById(rid));
	}
	@PostMapping("/update")
	public ResponseData updateRole(@RequestBody Role role) {
		int i=roleService.updateRole(role);
		if(i>0) {
			return DefaultResponseData.successResponseData(i);
		}else {
			return DefaultResponseData.UPDATE_ERROR;
		}
	}
	
	@GetMapping("/getOwnPermission")
	public ResponseData getOwnPermission(Integer rid) {
		List<Integer> pids=roleService.getOwnPermission(rid);
		return DefaultResponseData.successResponseData(pids);
	}
	
	@PostMapping("/setOwnPermission")
	public ResponseData setOwnPermission(@RequestBody JSONObject jsonObject) {
		Integer rid=jsonObject.getInteger("rid");
		List<Integer> pids = jsonObject.getObject("pids", new ArrayList<Integer>().getClass());
		List<Integer> oldPids = jsonObject.getObject("oldPids", new ArrayList<Integer>().getClass());
		
		roleService.setOwnPermission(rid,pids,oldPids);
		return null;
	}
}
