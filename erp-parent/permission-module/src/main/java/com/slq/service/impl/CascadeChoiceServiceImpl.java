package com.slq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slq.common.CascadeChoice;
import com.slq.constant.Constant;
import com.slq.dao.ICascadeChoiceDao;
import com.slq.mapper.CascadeChoiceMapper;
import com.slq.service.ICascadeChoiceService;
@Service
public class CascadeChoiceServiceImpl implements ICascadeChoiceService{

	/***
	 * redis缓存的key
	 */
	//权限的存储数据Id
	private String CASCADE_MENU_PERMISSION_KEY="cascade_menu_permission";

	@Autowired
	private CascadeChoiceMapper cascadeChoiceMapper;
	@Autowired
	private ICascadeChoiceDao cascadeChoiceDao;
	@Override
	public List<CascadeChoice> getCascadePermission() {
		String key=CASCADE_MENU_PERMISSION_KEY;
		List<CascadeChoice> cascadeChoices= cascadeChoiceDao.getCascadeChoicePermissionCache(key);
		if(cascadeChoices==null||cascadeChoices.isEmpty()) {
			cascadeChoices = cascadeChoiceMapper.getCascadePermission();
			boolean fals = cascadeChoiceDao.setCascadeChoicePermissionCache(key, cascadeChoices);
		}
		// TODO Auto-generated method stub
		return cascadeChoices;
	}

	
}
