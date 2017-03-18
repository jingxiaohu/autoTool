package com.jxh.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 随机数管理
 * @author jingxiaohu
 *
 */
public class RandomUtil {
	/**
	 * 返回16位纯数字随机数
	 */
	public static String return16(){
		return new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+RandomStringUtils.random(4,false,true);  
	}
	
	
}
