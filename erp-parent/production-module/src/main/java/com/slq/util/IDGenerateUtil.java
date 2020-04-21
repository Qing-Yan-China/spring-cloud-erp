package com.slq.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;


public class IDGenerateUtil {

	final static String PATTERN = "yyyyMMdd";
	// 默认的流水号
	final static String SERIAL_NUMBER = "0";
	//填充的0的个数
	final static int LEFT_PAD=6;
	
	final static String PATH="id.properties";
	public static String getDate(String pattern) {
		Date date = new Date();
		if(pattern==null)pattern=SERIAL_NUMBER;
		SimpleDateFormat dataFormat = new SimpleDateFormat(pattern);
		return dataFormat.format(date);
	}
	public static FileOutputStream getOutputStream(String path) {
		String absolute_path;
		try {
			absolute_path=IDGenerateUtil.class.getResource("/"+path).toURI().getPath();
			return new FileOutputStream(absolute_path);
		} catch (URISyntaxException | FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
		
	}
	public static String getID(String prefix, String pattern) {
		String id=prefix;
		id+=getDate(pattern);
		String path=PATH;
		InputStream inputStream= IDGenerateUtil.class.getClassLoader().getResourceAsStream(path);
		Properties properties=new Properties();
		try {
			properties.load(inputStream);
			Integer serialNumber;
			String value;
			if(isFirst_day_of_the_month()) {//是本月第一天
				value=SERIAL_NUMBER;
			}else {
				value= properties.getProperty(prefix);
				//如果没有这个前缀
				if(value==null||value.equals("")) {
					value=SERIAL_NUMBER;
				}
			}
			serialNumber = Integer.parseInt(value);
			++serialNumber;
			properties.put(prefix, serialNumber.toString());
			FileOutputStream outputStream=getOutputStream(path);
			properties.store(outputStream, null);
//			outputStream.flush();
//			outputStream.close();
			String suffix = StringUtils.leftPad(serialNumber.toString(), LEFT_PAD, '0');
			id+=suffix;
			return id;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getID(String prefix) {
		return getID(prefix, PATTERN);
	}

	public static boolean isFirst_day_of_the_month() {
		// 是否为本月第一天
		Calendar cale = Calendar.getInstance();
		int day = cale.get(Calendar.DATE);
		return day == 1;
	}
}
