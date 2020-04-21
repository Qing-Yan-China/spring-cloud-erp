package com.slq.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slq.common.Params;
import com.slq.mapper.RoleMapper;
import com.slq.pojo.Role;
import com.slq.service.IRoleService;
import com.slq.util.PinYingUtil;

@Service
@Transactional
@CacheConfig(cacheNames = "myCache")
public class RoleServiceImpl implements IRoleService{

	@Autowired
	private RoleMapper roleMapper;
	
	@Cacheable(keyGenerator = "keyGenerator")  //(key = "#root.methodName+#params")
	@Override
	public PageInfo<Role> getRolePageInfo(Params params) {
		return PageHelper.startPage(params.getPageNum(),params.getPageSize())
					.doSelectPageInfo(()->
					
						roleMapper.getRoleList(params.getKeyWord(),params.getOrderingRule())
					
					);
	}
	@Transactional
	@CacheEvict(allEntries = true)
	@Override
	public int insertRole(Role role) {
		//获取角色名称的首字母
		role.setInitial(PinYingUtil.getShortPinyin(role.getRname()));
		role.setCreateTime(new Date());
		roleMapper.insertRole(role);
		if(role.getRid()>0) {
			return role.getRid();
		}else {
			throw new RuntimeException();
		}
	}

	@CacheEvict(allEntries = true)
	@Override
	public int deleteRole(Integer rId) {
		//删除role_permission中对应的rId
		int i = roleMapper.delete_RolePermission(rId);
		i = roleMapper.deleteRole(rId);
		return 0;
	}
	@Cacheable(keyGenerator = "keyGenerator")
	@Override
	public Role getRoleById(Integer rid) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleById(rid);
	}
	@CacheEvict(allEntries = true)
	@Override
	public int updateRole(Role role) {
		role.setInitial(PinYingUtil.getShortPinyin(role.getRname()));
		role.setUpdateTime(new Date());
		int i = roleMapper.updateRole(role);
		if(i>0) {
			return i;
		}else {
			throw new RuntimeException();
		}
	}
	@Cacheable(keyGenerator = "keyGenerator")
	@Override
	public List<Integer> getOwnPermission(Integer rid) {
		List<Integer> pids = roleMapper.getOwnPermission(rid);
		return pids;
	}
	//设置它返回数据更新到缓存名称为 getOwnPermission[rid]这个中去
	@CachePut(key = " 'getOwnPermission[' + #rid + ']' ")
	@Transactional
	@Override
	public List<Integer> setOwnPermission(Integer rid, List<Integer> pids, List<Integer> oldPids) {
		int i;
		if(oldPids!=null&&!oldPids.isEmpty()) {
			//将这个角色之前的所有相关联的权限
			i=roleMapper.delete_roleOwnPermission(rid);
			if(i<=0) {
				throw new RuntimeException();
			}
		}
		//设置权限
		i=roleMapper.set_roleOwnPermission(rid,pids);
		if(i<=0) {
			throw new RuntimeException();
		}
		return pids;
	}
	//可能需要加清除缓存
	//设置角色状态的方法
	@CacheEvict(allEntries = true)
	@Override
	public int setRoleState(Integer rid, Integer newStatus) {
		int i = roleMapper.setRoleState(rid,newStatus);
		if(i>0) {
			return i;
		}
		throw new RuntimeException();
	}
	/***
	 * 获取可用的角色选择器
	 */
	@Cacheable(keyGenerator = "keyGenerator")
	@Override
	public List<Role> getUsableRoleChooser() {
		
		return roleMapper.getUsableRoleChooser();
	}
	//获取用户拥有的权限集合
	@Override
	public List<String> getUserOwnRole(Integer id) {
		
		return roleMapper.getUserOwnRole(id);
	}

}
