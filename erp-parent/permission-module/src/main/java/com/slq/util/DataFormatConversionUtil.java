package com.slq.util;

import com.alibaba.fastjson.JSON;

/***
 * 数据格式转换工具类
 * @author 孙陆泉
 *
 */
public class DataFormatConversionUtil {

	public static String toJSON(Object obj) {
		return JSON.toJSONString(obj);
	}
}
