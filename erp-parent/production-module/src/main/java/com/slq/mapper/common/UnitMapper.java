package com.slq.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.slq.pojo.common.Unit;

@Mapper
public interface UnitMapper extends BaseMapper<Unit>{

	
	public Unit getUnitByTableColumn(String table_column);
	
	/***
	 * 获取单位的值  是查询unitValue表
	 * @param id
	 * @return
	 */
	public List<String> getUnitById(String id);
}
