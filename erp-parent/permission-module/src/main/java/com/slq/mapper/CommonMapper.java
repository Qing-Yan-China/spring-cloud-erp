package com.slq.mapper;

import java.util.HashSet;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.slq.common.Sort;
import com.slq.pojo.TreeMenu;


@Mapper
public interface CommonMapper {

	Integer getDescSort(Sort sort);

	List<TreeMenu> getTreeMenu(Integer type);

	List<TreeMenu> getTreeMenuChild(Integer pId);

	HashSet<Integer> getTreeMenuIds(Integer id);
	
}
