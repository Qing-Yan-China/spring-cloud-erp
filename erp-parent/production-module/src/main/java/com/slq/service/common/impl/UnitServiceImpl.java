package com.slq.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slq.mapper.common.UnitMapper;
import com.slq.pojo.common.Unit;
import com.slq.service.common.IUnitService;

@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper,Unit> implements IUnitService{

	@Autowired
	private UnitMapper unitMapper;
	@Override
	public List<Unit> getUnitValueByKey(String table_column) {
		QueryWrapper<Unit> query=new QueryWrapper<Unit>();
		query.like("unit_key", table_column);
		this.getBaseMapper().selectOne(query);
		return null;//unitMapper.getUnitValueByKey(key);
	}

	@Override
	public List<Unit> getUnitKeyAll() {
		QueryWrapper<Unit> query=new QueryWrapper<Unit>();
		List<Unit> units=this.getBaseMapper().selectList(query);
		System.out.println(JSON.toJSONString(units));
		return units;
	}

}
