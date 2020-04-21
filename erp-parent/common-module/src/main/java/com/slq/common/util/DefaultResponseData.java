package com.slq.common.util;




public class DefaultResponseData {

	 static Integer NOT_LOGIN_ERROR_CORD=302;//没有登录状态码
	 
	 static Integer LOGIN_HAS_EXPIRED_CORD=303;//身份过期了
	
	 static Integer UNAUTH_CORD=301;//未授权的
	 
	 static String NOT_LOGIN_ERROR_MESSAGE="未登录!";
	 
	 static String UNAUTH_MESSAGE="权限不足!";
	 
	 static String LOGOUT_MESSAGE="账号已安全退出";
	
	 static Integer RESPONSE_SUCCESS_CORD=200;//响应成功
	 
	public static ResponseData  LOGIN_HAS_EXPIRED=new ResponseData(LOGIN_HAS_EXPIRED_CORD, "身份过期!");//login has expired
	public  static ResponseData NOT_LOGIN=new ResponseData(NOT_LOGIN_ERROR_CORD, NOT_LOGIN_ERROR_MESSAGE,true,"/login.html");
	public static ResponseData UNAUTH=new ResponseData(UNAUTH_CORD, UNAUTH_MESSAGE);
	public static  String ADD_SUCCESS = new String("添加成功");
    public static  String UPDATE_SUCCESS = new String("更新成功");
    public static  String DELETE_SUCCESS = new String("删除成功");
    public static  String RESPONSE_SUCCESS = new String("响应成功");
    
    
    public static final ResponseData RESOURCE_ERROR = new ResponseData(500,"数据获取失败");
    public static final ResponseData ADD_ERROR = new ResponseData(500,"添加失败");
    public static final ResponseData UPDATE_ERROR = new ResponseData(500,"更新失败");
    public static final ResponseData DELETE_ERROR = new ResponseData(500,"删除失败");
    public static final ResponseData RESET_ERROR = new ResponseData(500,"重置失败");
    public static final ResponseData DISPATCH_ERROR = new ResponseData(500,"分配失败");
    public static final ResponseData CLEAR_ERROR = new ResponseData(500,"清空失败");
    public static ResponseData successResponseData(String message,Object data) {
		if(message==null) {
			message=RESPONSE_SUCCESS;
		}
    	return new ResponseData(RESPONSE_SUCCESS_CORD,message, data);
	}
    public static ResponseData successResponseData(String message) {
    	return successResponseData(message, null);
	}
    public static ResponseData successResponseData(Object data) {
    	return successResponseData(null, data);
	}
    public static ResponseData errorResponseData(String message) {
    	return new ResponseData(500, message);
    }
    
//    public static ResponseData response(Object data,String success,ResponseData error) {
//    	if(data.getClass()==int.class||data.getClass()==Integer) {
//    		
//    	}
//    }
}
