package com.slq.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;

/***
 * 获取中文首字母的 工具类
 * @author 孙陆泉
 *
 */
public class PinYingUtil {

	public static String getShortPinyin(String str,boolean retain) {
		return getPinyin(str,true,retain);
	}
	/***
	 * 你们直接使用这个方法就行了
	 * @param str
	 * @return
	 */
	public static String getShortPinyin(String str) {
		return getShortPinyin(str,true);
	}
	/***
	 * 
	 * @param str 需要操作的字符串
	 * @param shortPinyin true获取首字母拼音  false 获取全拼音
	 * @param retain 保留空格啥的
	 * @return
	 */
	public static String getPinyin(String str,boolean shortPinyin,boolean retain) {
		if(str.isEmpty()) {
			return "";
		}
		StringBuilder builder=new StringBuilder();
		char [] array=str.toCharArray();
		for (char c : array) {
			String [] temp=PinyinHelper.toHanyuPinyinStringArray(c);
			
			if(ArrayUtils.isNotEmpty(temp)) {
				if(StringUtils.isNotBlank(temp[0])) {
					if(shortPinyin) {//获取的是首字母
						builder.append(temp[0].charAt(0));
					}else {//获取的是全拼
						builder.append(temp[0].replaceAll("\\d",""));
					}
				}
			}else {
				//可能是空格或者数字字母等  不是中文的
				if(retain) {
					builder.append(c);
				}
			}
		}
		return builder.toString();
	}
}
