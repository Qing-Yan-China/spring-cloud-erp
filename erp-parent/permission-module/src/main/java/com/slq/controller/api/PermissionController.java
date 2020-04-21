package com.slq.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.slq.common.Params;
import com.slq.constant.Constant;
import com.slq.exception.NotToBeDeletedException;
import com.slq.overwrite.MyPageInfo;
import com.slq.pojo.Permission;
import com.slq.service.IPermissionService;
import com.slq.util.DefaultResponseData;
import com.slq.util.OrderingRule;
import com.slq.util.ResponseData;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

	@Autowired
	private IPermissionService permissionService;
	@RequestMapping("/search")
	public ResponseData getPermissionPage(@RequestBody Params params) {
		PageInfo<Permission> permissionPageInfo = permissionService.getPermissionPage(params);
		System.out.println(JSON.toJSONString(permissionPageInfo));
		return DefaultResponseData.successResponseData(permissionPageInfo);
	}
	
//	@RequestMapping("/searchPermission")
//	public ResponseData getPermissionPage(@RequestBody JSONObject obj) {
//		
//		MyPageInfo<Permission> permissionPage= obj.getObject("permissionPage",MyPageInfo.class);
//		OrderingRule order=obj.getObject("order", OrderingRule.class);
//		System.out.println(JSON.toJSONString(permissionPage));
//		System.out.println(JSON.toJSONString(order));
//		permissionPage = permissionService.getPermissionPage(permissionPage,order);
//		return DefaultResponseData.successResponseData( permissionPage);
//	}
	
	@RequestMapping("/insert")
	public ResponseData insertPermission(Permission permission) {
		System.out.println(permission);
		permissionService.insertPermission(permission);
		if(permission.getpId()==null)
			return DefaultResponseData.ADD_ERROR;
		return DefaultResponseData.successResponseData( permission.getpId() );
	}
	
	@RequestMapping("/getById")
	public ResponseData getPermissionById(Integer pId) {
		System.out.println("pId:"+pId);
		Permission permission = permissionService.getPermissionById(pId);
		return DefaultResponseData.successResponseData(permission);
	}
	
	@RequestMapping("/update")
	public ResponseData updatePermission(Permission permission) {
		System.out.println(permission);
		int i = permissionService.updatePermission(permission);
		if(i>0) {
			return DefaultResponseData.successResponseData(i);
		}else {
			return DefaultResponseData.UPDATE_ERROR;
		}
	}
	
	@RequestMapping("/updateStatus/{pId}/{newPermissionState}")
	public ResponseData updatePermission(@PathVariable("pId")Integer pId,@PathVariable("newPermissionState")Integer newPermissionState) {
		int i = permissionService.updatePermissionStatus(pId,newPermissionState);
		return DefaultResponseData.successResponseData(i);
	}
	@RequestMapping("/delete/{pId}")
	public ResponseData deletePermission(@PathVariable("pId")Integer pId) {
		int i;
		try {
			i = permissionService.deletePermission(pId);
			return i>0?DefaultResponseData.successResponseData(DefaultResponseData.DELETE_SUCCESS,i):DefaultResponseData.DELETE_ERROR;
		} catch (NotToBeDeletedException e) {
			return DefaultResponseData.errorResponseData(e.getMessage());
		}
	}
}
