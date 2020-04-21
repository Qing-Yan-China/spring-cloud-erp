package com.slq.service;

import java.util.List;

import com.slq.common.Sort;
import com.slq.pojo.TreeMenu;

public interface ICommonService {

	Sort getDescSort(Sort sort);

	List<TreeMenu> getTreeMenu(Integer type);

	//获取左侧导航菜单数据
	List<TreeMenu> getTreeMenus();

	
}
