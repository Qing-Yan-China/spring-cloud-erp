package com.slq.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.slq.common.Sort;
import com.slq.constant.Constant;
import com.slq.mapper.CommonMapper;
import com.slq.pojo.TreeMenu;
import com.slq.service.ICommonService;
import com.slq.util.ActiveUser;
@Service
@CacheConfig(cacheNames = {"myCache"})
public class CommonServiceImpl implements ICommonService{

	@Autowired
	private CommonMapper commonMapper;
	
	@Cacheable(keyGenerator = "keyGenerator")
	@Override
	public Sort getDescSort(Sort sort) {
		sort.setmaxSortNum(commonMapper.getDescSort(sort));
		return sort;
	}
	@Cacheable(keyGenerator = "keyGenerator")
	@Override
	public List<TreeMenu> getTreeMenu(Integer type) {
		List<TreeMenu> treeMenu=commonMapper.getTreeMenu(type);
		System.out.println(JSON.toJSONString(treeMenu));
		return treeMenu;
	}
	
	@Override
	public List<TreeMenu> getTreeMenus() {
		
		Subject subject=SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		List<TreeMenu> treeMenu=null;
		//获取所有的菜单
		treeMenu=commonMapper.getTreeMenu(Constant.MENU_PERMISSION);
		System.out.println(JSON.toJSONString(treeMenu));
		//如果用户不是超级管理员 就进行菜单的过滤
		if(activeUser.getUser().getIsadmin()!=Constant.USER_TYPE_SUPER) {
			HashSet<Integer> pids=commonMapper.getTreeMenuIds(activeUser.getUser().getId());
			filterMenu(treeMenu, pids);
		}
		return treeMenu;
	}
	public void filterMenu(List<TreeMenu> menus,HashSet<Integer> pids) {
		Iterator<TreeMenu> iterator = menus.iterator();
		TreeMenu treeMenu=null;
		while (iterator.hasNext()) {
			treeMenu=iterator.next();
			if(treeMenu!=null) {
				if(!pids.contains(treeMenu.getId())) {
					iterator.remove();
					continue;
				}
				if(treeMenu.getChildren()!=null) {
					filterMenu(treeMenu.getChildren(), pids);
				}
			}
		}
	}
	
	

}
