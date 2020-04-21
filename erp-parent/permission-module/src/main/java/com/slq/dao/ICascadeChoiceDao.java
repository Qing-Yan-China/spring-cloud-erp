package com.slq.dao;

import java.util.List;

import com.slq.common.CascadeChoice;
import com.slq.common.Time;

public interface ICascadeChoiceDao {

	public abstract List<CascadeChoice> getCascadeChoicePermissionCache(String key);
	
	public abstract boolean setCascadeChoicePermissionCache(String key,List<CascadeChoice> cascadeChoices,Time time);
	
	public abstract boolean setCascadeChoicePermissionCache(String key,List<CascadeChoice> cascadeChoices);
}
