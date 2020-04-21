package com.slq.dao.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.slq.common.CascadeChoice;
import com.slq.common.Time;
import com.slq.constant.RedisConstant;
import com.slq.dao.ICascadeChoiceDao;
@Repository
public class CascadeChoiceDaoImpl implements ICascadeChoiceDao{

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public List<CascadeChoice> getCascadeChoicePermissionCache(String key) {
		System.out.println(key);
		List<CascadeChoice> cascadeChoices =null;
		if (redisTemplate.getExpire(key)>0)
			cascadeChoices = (List<CascadeChoice>)(Object)redisTemplate.opsForList().range(key, 0, -1);
		System.out.println("cascadeChoices:"+JSON.toJSONString(cascadeChoices));
		return cascadeChoices;
	}

	@Override
	public boolean setCascadeChoicePermissionCache(String key, List<CascadeChoice> cascadeChoices,Time time) {
		long i =0; 
		for (CascadeChoice cascadeChoice : cascadeChoices) {
			i=redisTemplate.opsForList().rightPush(key, cascadeChoice);
		}
		//设置它的存活时间
		redisTemplate.expire(key, time.getTime(), time.getTimeUnit());
		return i>0?true:false;
	}

	@Override
	public boolean setCascadeChoicePermissionCache(String key, List<CascadeChoice> cascadeChoices) {
		this.setCascadeChoicePermissionCache(key, cascadeChoices,RedisConstant.DEFAULT_EXPIRE );
		return false;
	}
	
	
	
	
	
}
