package com.slq.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.slq.common.Params;
import com.slq.pojo.Role;

public interface IRoleService {

	PageInfo<Role> getRolePageInfo(Params params);

	int insertRole(Role role);

	int deleteRole(Integer rId);

	Role getRoleById(Integer rid);

	int updateRole(Role role);

	/***
	 * 获取角色拥有的权限id
	 * @param rid
	 * @return
	 */
	List<Integer> getOwnPermission(Integer rid);

	List<Integer> setOwnPermission(Integer rid, List<Integer> pids, List<Integer> oldPids);

	int setRoleState(Integer rid, Integer newStatus);

	
	List<Role> getUsableRoleChooser();

	
	List<String> getUserOwnRole(Integer id);

}
