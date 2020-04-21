package com.slq.exception;
/***
 * 不能删除的异常
 * @author 孙陆泉
 *
 */
public class NotToBeDeletedException extends Exception{

	public static final String DEFAULT_DETAIL_MESSAGE="不能删除的";
	
	public NotToBeDeletedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NotToBeDeletedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotToBeDeletedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotToBeDeletedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
