package com.slq.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.slq.common.Params;
import com.slq.exception.NotToBeDeletedException;
import com.slq.overwrite.MyPageInfo;
import com.slq.pojo.Permission;
import com.slq.util.OrderingRule;

public interface IPermissionService {

	PageInfo<Permission> getPermissionPage(Params params);
	
	//MyPageInfo<Permission> getPermissionPage(MyPageInfo<Permission> permissionPage, OrderingRule order);

	void insertPermission(Permission permission);

	Permission getPermissionById(Integer pId);

	int updatePermission(Permission permission);

	int updatePermissionStatus(Integer pId, Integer oldPermissionState);

	int deletePermission(Integer pId) throws NotToBeDeletedException;

	List<String> getUserOwnPermission(Integer id);

	


}
