package com.slq.constant;

import com.slq.dao.ICascadeChoiceDao;
import com.slq.dao.impl.CascadeChoiceDaoImpl;

public interface Constant {

	Integer RESOURCE_PERMISSION=1;
	Integer MENU_PERMISSION=2;
	
	String ORDER_ASC="asc";
	String ORDER_DESC="desc";
	/**
     * 用户类型
     */
	Integer USER_TYPE_SUPER = 1;
	Integer USER_TYPE_MANAGER=2;
    Integer USER_TYPE_NORMAL = 3;
    
    /***
     * 用户状态
     */
    Integer USER_STATE_VALID=1;
    Integer USER_STATE_FREEZE=2;
 
    /***
     * 所有的状态
     */
    Integer VALID_STATE=1;
    Integer FREEZE_STATE=2;
    /***
     * 用户默认的密码和头像
     */
    String DEFULAT_USER_PASSWORD="123";
	
	String DEFAULT_USER_HEADPORTRAIT="default_head_portrait.png";


}
