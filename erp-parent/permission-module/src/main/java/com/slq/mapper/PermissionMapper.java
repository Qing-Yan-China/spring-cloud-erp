package com.slq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.slq.pojo.Permission;
import com.slq.util.OrderingRule;


@Mapper
public interface PermissionMapper {

	public List<Permission> getPermissionList(@Param("keyWord")String keyWord,@Param("orderingRule") OrderingRule order);

	public void insertPermission(Permission permission);

	public Permission getPermissionById(Integer pId);

	public int updatePermission(Permission permission);

	public int updatePermissionStatus(@Param("pId")Integer pId, @Param("newPermissionState")Integer newPermissionState);

	public int deletePermission(Integer pId);

	public int delete_rolePermission(Integer pId);

	public Integer getChildrenPermissionCountBypId(Integer pId);

	public List<String> getUserOwnPermission(@Param("id")Integer id);
}
