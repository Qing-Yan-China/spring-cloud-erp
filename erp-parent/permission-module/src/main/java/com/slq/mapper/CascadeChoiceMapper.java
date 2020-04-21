package com.slq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.slq.common.CascadeChoice;
@Mapper
public interface CascadeChoiceMapper {

	List<CascadeChoice> getCascadePermission();

	List<CascadeChoice> getCascadePermissionChild(Integer parentId);
}
