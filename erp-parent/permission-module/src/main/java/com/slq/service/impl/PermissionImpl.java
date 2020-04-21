package com.slq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slq.common.Params;
import com.slq.constant.Constant;
import com.slq.exception.NotToBeDeletedException;
import com.slq.mapper.PermissionMapper;
import com.slq.overwrite.MyPageInfo;
import com.slq.pojo.Permission;
import com.slq.service.IPermissionService;
import com.slq.util.OrderingRule;
import com.slq.util.PinYingUtil;
@Service
@Transactional
@CacheConfig(cacheNames = "myCache")
public class PermissionImpl implements IPermissionService{

	@Autowired
	private PermissionMapper permissionMapper;
	
	@Cacheable(keyGenerator = "keyGenerator")
	@Override
	public PageInfo<Permission> getPermissionPage(Params params) {
		return PageHelper.startPage(params.getPageNum(),params.getPageSize())
				.doSelectPageInfo(()->
					permissionMapper.getPermissionList(params.getKeyWord(), params.getOrderingRule())
				);
	}
//	@Override
//	public MyPageInfo<Permission> getPermissionPage(MyPageInfo<Permission> permissionPage,OrderingRule order) {
//		String keyWord=permissionPage.getKeyWord();
//		PageInfo<Permission> permissionPageInfo = PageHelper.startPage(permissionPage.getPageNum(), permissionPage.getPageSize())
//					.doSelectPageInfo(()->
//					permissionMapper.getPermissionList(keyWord,order)
//					);
//		
//		//permissionPage=(MyPageInfo) permissionPageInfo;
//		//permissionPage.setList(permissionMapper.getPermissionList(permissionPage.getKeyWord(),order));
//		System.out.println(permissionPageInfo.getList());
//		for (Permission permission : permissionPageInfo.getList()) {
//			System.out.println(permission);
//		}
//		return null;
//	}
	@CacheEvict(allEntries = true)
	@Transactional
	@Override
	public void insertPermission(Permission permission) {
		//获取权限名称的拼音首字母
		permission.setInitial(PinYingUtil.getShortPinyin(permission.getpName()));
		setPermission(permission);
		
		permissionMapper.insertPermission(permission);
		if(permission.getpId()!=null&&permission.getpId()>0) {
			//清除缓存
		}else {
			throw new RuntimeException();
		}
	}
	@Cacheable(keyGenerator = "keyGenerator")
	@Override
	public Permission getPermissionById(Integer pId) {
		return permissionMapper.getPermissionById(pId);
	}
	public void setPermission(Permission permission) {
		if(permission.getpType()==1) {
			permission.setUrl(null);
			permission.setIcon(null);
			permission.setComponentName(null);
		}else {
			permission.setResource(null);
		}
	}
	@CacheEvict(allEntries = true)
	@Override
	public int updatePermission(Permission permission) {
		setPermission(permission);
		permission.setInitial(PinYingUtil.getShortPinyin(permission.getpName()));
		int i = permissionMapper.updatePermission(permission);
		if(i>0) {
			//清除缓存
			return i;
		}else {
			throw new RuntimeException();
		}
	}
	@CacheEvict(allEntries = true)
	@Override
	public int updatePermissionStatus(Integer pId, Integer newPermissionState) {
		int i = permissionMapper.updatePermissionStatus(pId,newPermissionState);
		if(i>0) {
			return newPermissionState;
		}else {
			throw new RuntimeException();
		}
	}
	@Transactional
	@CacheEvict(allEntries = true)
	@Override
	public int deletePermission(Integer pId) throws NotToBeDeletedException {
		//判断是否为父级权限 如果是父级权限就不能删除
		//删除对应的role_permission
		int i = permissionMapper.getChildrenPermissionCountBypId(pId);
		if(i>0) {
			String detailMessage="删除失败!该权限下还有子权限不能删除";
			throw new NotToBeDeletedException(detailMessage);
		}
		i= permissionMapper.delete_rolePermission(pId);
		i = permissionMapper.deletePermission(pId);
		if(i>0) {
			return i;
		}else {
			throw new RuntimeException();
		}
	}
	//获取用户拥有的权限
	@Override
	public List<String> getUserOwnPermission(Integer id) {
		
		return permissionMapper.getUserOwnPermission(id);
	}


}
