package com.slq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.slq.pojo.Role;
import com.slq.util.OrderingRule;


@Mapper
public interface RoleMapper {

	List<Role> getRoleList(@Param("keyWord")String keyWord, @Param("orderingRule")OrderingRule orderingRule);

	void insertRole(Role role);

	Role getRoleById(Integer rid);

	int updateRole(Role role);

	List<Integer> getOwnPermission(Integer rid);

	int delete_roleOwnPermission(Integer rid);

	int set_roleOwnPermission(@Param("rid")Integer rid,@Param("pids")List<Integer> pids);

	int delete_RolePermission(Integer rId);

	int deleteRole(Integer rId);

	int setRoleState(@Param("rid")Integer rid, @Param("newStatus")Integer newStatus);

	List<Role> getUsableRoleChooser();

	List<String> getUserOwnRole(Integer id);

}
